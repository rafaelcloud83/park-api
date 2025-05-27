package edu.rafael.park_api;

import edu.rafael.park_api.web.dto.EstacionamentoCreateDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.reactive.server.WebTestClient;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Sql(scripts = "/sql/estacionamentos/estacionamentos-insert.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(scripts = "/sql/estacionamentos/estacionamentos-delete.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
public class EstacionamentoIT {

    @Autowired
    WebTestClient testClient;

    @Test
    public void checkInEstacionamento_ComDadosValidos_RetornarLocationEstacionamentoStatus201() {
        EstacionamentoCreateDto createDto = EstacionamentoCreateDto.builder()
                .placa("ABC-1234")
                .marca("Fiat")
                .modelo("Uno")
                .cor("Branco")
                .clienteCpf("38641465006")
                .build();

        testClient
                .post()
                .uri("api/v1/estacionamentos/check-in")
                .contentType(MediaType.APPLICATION_JSON)
                .headers(JwtAuthentication.getHeaderAuthorization(testClient, "admin@email.com", "123456"))
                .bodyValue(createDto)
                .exchange()
                .expectStatus().isCreated()
                .expectHeader().exists(HttpHeaders.LOCATION)
                .expectBody()
                .jsonPath("placa").isEqualTo("ABC-1234")
                .jsonPath("marca").isEqualTo("Fiat")
                .jsonPath("modelo").isEqualTo("Uno")
                .jsonPath("cor").isEqualTo("Branco")
                .jsonPath("clienteCpf").isEqualTo("38641465006")
                .jsonPath("recibo").exists()
                .jsonPath("dataEntrada").exists()
                .jsonPath("vagaCodigo").exists();
    }

    @Test
    public void checkInEstacionamento_ComPerfilCliente_RetornarErrorMessageComStatus403() {
        EstacionamentoCreateDto createDto = EstacionamentoCreateDto.builder()
                .placa("ABC-1234")
                .marca("Fiat")
                .modelo("Uno")
                .cor("Branco")
                .clienteCpf("38641465006")
                .build();

        testClient
                .post()
                .uri("api/v1/estacionamentos/check-in")
                .contentType(MediaType.APPLICATION_JSON)
                .headers(JwtAuthentication.getHeaderAuthorization(testClient, "maria@email.com", "123456"))
                .bodyValue(createDto)
                .exchange()
                .expectStatus().isForbidden()
                .expectBody()
                .jsonPath("status").isEqualTo("403")
                .jsonPath("path").isEqualTo("/api/v1/estacionamentos/check-in")
                .jsonPath("method").isEqualTo("POST");
    }

    @Test
    public void checkInEstacionamento_ComDadosInvalidos_RetornarErrorMessageComStatus422() {
        EstacionamentoCreateDto createDto = EstacionamentoCreateDto.builder()
                .placa("")
                .marca("")
                .modelo("")
                .cor("")
                .clienteCpf("")
                .build();

        testClient
                .post()
                .uri("api/v1/estacionamentos/check-in")
                .contentType(MediaType.APPLICATION_JSON)
                .headers(JwtAuthentication.getHeaderAuthorization(testClient, "admin@email.com", "123456"))
                .bodyValue(createDto)
                .exchange()
                .expectStatus().isEqualTo(422)
                .expectBody()
                .jsonPath("status").isEqualTo("422")
                .jsonPath("path").isEqualTo("/api/v1/estacionamentos/check-in")
                .jsonPath("method").isEqualTo("POST");
    }

    @Test
    public void checkInEstacionamento_ComCpfInexistente_RetornarErrorMessageComStatus404() {
        EstacionamentoCreateDto createDto = EstacionamentoCreateDto.builder()
                .placa("ABC-1234")
                .marca("Fiat")
                .modelo("Uno")
                .cor("Branco")
                .clienteCpf("50006859038")
                .build();

        testClient
                .post()
                .uri("api/v1/estacionamentos/check-in")
                .contentType(MediaType.APPLICATION_JSON)
                .headers(JwtAuthentication.getHeaderAuthorization(testClient, "admin@email.com", "123456"))
                .bodyValue(createDto)
                .exchange()
                .expectStatus().isNotFound()
                .expectBody()
                .jsonPath("status").isEqualTo("404")
                .jsonPath("path").isEqualTo("/api/v1/estacionamentos/check-in")
                .jsonPath("method").isEqualTo("POST");
    }

    @Sql(scripts = "/sql/estacionamentos/estacionamento-insert-vagas-ocupadas.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "/sql/estacionamentos/estacionamento-delete-vagas-ocupadas.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    @Test
    public void checkInEstacionamento_ComVagasOcupadas_RetornarErrorMessageComStatus404() {
        EstacionamentoCreateDto createDto = EstacionamentoCreateDto.builder()
                .placa("ABC-1234")
                .marca("Fiat")
                .modelo("Uno")
                .cor("Branco")
                .clienteCpf("38641465006")
                .build();

        testClient
                .post()
                .uri("api/v1/estacionamentos/check-in")
                .contentType(MediaType.APPLICATION_JSON)
                .headers(JwtAuthentication.getHeaderAuthorization(testClient, "admin@email.com", "123456"))
                .bodyValue(createDto)
                .exchange()
                .expectStatus().isNotFound()
                .expectBody()
                .jsonPath("status").isEqualTo("404")
                .jsonPath("path").isEqualTo("/api/v1/estacionamentos/check-in")
                .jsonPath("method").isEqualTo("POST");
    }

    @Test
    public void getByReciboEstacionamento_ComPerfilAdmin_RetornarDadosComStatus200() {
        testClient
                .get()
                .uri("api/v1/estacionamentos/check-in/{recibo}", "20250524-101300")
                .headers(JwtAuthentication.getHeaderAuthorization(testClient, "admin@email.com", "123456"))
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("placa").isEqualTo("FIT-1020")
                .jsonPath("marca").isEqualTo("FIAT")
                .jsonPath("modelo").isEqualTo("PALIO")
                .jsonPath("cor").isEqualTo("VERDE")
                .jsonPath("clienteCpf").isEqualTo("81315448009")
                .jsonPath("recibo").isEqualTo("20250524-101300")
                .jsonPath("dataEntrada").isEqualTo("2025-05-24 10:13:00")
                .jsonPath("vagaCodigo").isEqualTo("A-01");
    }

    @Test
    public void getByReciboEstacionamento_ComPerfilCliemte_RetornarDadosComStatus200() {
        testClient
                .get()
                .uri("api/v1/estacionamentos/check-in/{recibo}", "20250524-101300")
                .headers(JwtAuthentication.getHeaderAuthorization(testClient, "joao@email.com", "123456"))
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("placa").isEqualTo("FIT-1020")
                .jsonPath("marca").isEqualTo("FIAT")
                .jsonPath("modelo").isEqualTo("PALIO")
                .jsonPath("cor").isEqualTo("VERDE")
                .jsonPath("clienteCpf").isEqualTo("81315448009")
                .jsonPath("recibo").isEqualTo("20250524-101300")
                .jsonPath("dataEntrada").isEqualTo("2025-05-24 10:13:00")
                .jsonPath("vagaCodigo").isEqualTo("A-01");
    }

    @Test
    public void getByReciboEstacionamento_ComReciboInexistente_RetornarErrorMessageComStatus404() {
        testClient
                .get()
                .uri("api/v1/estacionamentos/check-in/{recibo}", "20250524-000000")
                .headers(JwtAuthentication.getHeaderAuthorization(testClient, "joao@email.com", "123456"))
                .exchange()
                .expectStatus().isNotFound()
                .expectBody()
                .jsonPath("status").isEqualTo("404")
                .jsonPath("path").isEqualTo("/api/v1/estacionamentos/check-in/20250524-000000")
                .jsonPath("method").isEqualTo("GET");
    }

    @Test
    public void checkOutEstacionamento_ComReciboExistente_RetornarDadosComStatus200() {
        testClient
                .put()
                .uri("api/v1/estacionamentos/check-out/{recibo}", "20250524-101300")
                .headers(JwtAuthentication.getHeaderAuthorization(testClient, "admin@email.com", "123456"))
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("placa").isEqualTo("FIT-1020")
                .jsonPath("marca").isEqualTo("FIAT")
                .jsonPath("modelo").isEqualTo("PALIO")
                .jsonPath("cor").isEqualTo("VERDE")
                .jsonPath("clienteCpf").isEqualTo("81315448009")
                .jsonPath("recibo").isEqualTo("20250524-101300")
                .jsonPath("dataEntrada").isEqualTo("2025-05-24 10:13:00")
                .jsonPath("vagaCodigo").isEqualTo("A-01")
                .jsonPath("dataSaida").exists()
                .jsonPath("valor").exists()
                .jsonPath("desconto").exists();
    }

    @Test
    public void checkOutEstacionamento_ComReciboInexistente_RetornarErrorMessageComStatus404() {
        testClient
                .put()
                .uri("api/v1/estacionamentos/check-out/{recibo}", "20250524-000000")
                .headers(JwtAuthentication.getHeaderAuthorization(testClient, "admin@email.com", "123456"))
                .exchange()
                .expectStatus().isNotFound()
                .expectBody()
                .jsonPath("status").isEqualTo("404")
                .jsonPath("path").isEqualTo("/api/v1/estacionamentos/check-out/20250524-000000")
                .jsonPath("method").isEqualTo("PUT");
    }

    @Test
    public void checkOutEstacionamento_ComPerfilCliente_RetornarErrorMessageComStatus403() {
        testClient
                .put()
                .uri("api/v1/estacionamentos/check-out/{recibo}", "20250524-101300")
                .headers(JwtAuthentication.getHeaderAuthorization(testClient, "maria@email.com", "123456"))
                .exchange()
                .expectStatus().isForbidden()
                .expectBody()
                .jsonPath("status").isEqualTo("403")
                .jsonPath("path").isEqualTo("/api/v1/estacionamentos/check-out/20250524-101300")
                .jsonPath("method").isEqualTo("PUT");
    }
}
