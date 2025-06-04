package edu.rafael.park_api.web.controller;

import edu.rafael.park_api.entity.ClienteVaga;
import edu.rafael.park_api.repository.projection.ClienteVagaProjection;
import edu.rafael.park_api.service.ClienteVagaService;
import edu.rafael.park_api.service.EstacionamentoService;
import edu.rafael.park_api.web.dto.EstacionamentoCreateDto;
import edu.rafael.park_api.web.dto.EstacionamentoResponseDto;
import edu.rafael.park_api.web.dto.PageableDto;
import edu.rafael.park_api.web.dto.mapper.ClienteVagaMapper;
import edu.rafael.park_api.web.dto.mapper.PageableMapper;
import edu.rafael.park_api.web.exception.ErrorMessage;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.headers.Header;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@Tag(name = "Estacionamentos", description = "Contém todas as operações relativas ao recurso de entrada e saída de veículos de um estacionamento")
@RestController
@RequestMapping("api/v1/estacionamentos")
@RequiredArgsConstructor
public class EstacionamentoController {

    private final EstacionamentoService estacionamentoService;
    private final ClienteVagaService clienteVagaService;

    @Operation(
            summary = "Operação de check-in de veículo",
            description = "Requisição exige um Bearer Token. Acesso restrito a ADMIN",
            security = @SecurityRequirement(name = "security"),
            responses = {
                    @ApiResponse(responseCode = "201", description = "Check-in realizado com sucesso",
                            headers = @Header(name = HttpHeaders.LOCATION, description = "URL do estacionamento criada"),
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = EstacionamentoResponseDto.class))),
                    @ApiResponse(responseCode = "401", description = "Token inválido ou expirado",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = ErrorMessage.class))),
                    @ApiResponse(responseCode = "403", description = "Usuário sem permissão de acesso",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = ErrorMessage.class))),
                    @ApiResponse(responseCode = "404", description = "Causas possíveis: <br/>" +
                            "- CPF do cliente não encontrado; <br/>" +
                            "- Nenhuma vaga livre foi encontrada;",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = ErrorMessage.class))),
                    @ApiResponse(responseCode = "422", description = "Estacionamento com dados inválidos",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = ErrorMessage.class)))
            }
    )
    @PostMapping("/check-in")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<EstacionamentoResponseDto> checkIn(@RequestBody @Valid EstacionamentoCreateDto dto) {
        ClienteVaga clienteVaga = ClienteVagaMapper.toClienteVaga(dto);
        estacionamentoService.checkIn(clienteVaga);
        EstacionamentoResponseDto responseDto = ClienteVagaMapper.toDto(clienteVaga);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequestUri().path("/{recibo}")
                .buildAndExpand(clienteVaga.getRecibo())
                .toUri();
        return ResponseEntity.created(location).body(responseDto);
    }

    @Operation(
            summary = "Buscar um veículo no estacionamento pelo recibo",
            description = "Requisição exige um Bearer Token. Acesso restrito a ADMIN ou CLIENTE",
            security = @SecurityRequirement(name = "security"),
            parameters = {
                    @Parameter(in = ParameterIn.PATH, name = "recibo", description = "Número do recibo gerado no check-in")
            },
            responses = {
                    @ApiResponse(responseCode = "200", description = "Veículo encontrado com sucesso",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = EstacionamentoResponseDto.class))),
                    @ApiResponse(responseCode = "401", description = "Token inválido ou expirado",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = ErrorMessage.class))),
                    @ApiResponse(responseCode = "403", description = "Usuário sem permissão de acesso",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = ErrorMessage.class))),
                    @ApiResponse(responseCode = "404", description = "Número de recibo não encontrado",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = ErrorMessage.class)))
            }
    )
    @GetMapping("/check-in/{recibo}")
    @PreAuthorize("hasAnyRole('ADMIN', 'CLIENTE')")
    public ResponseEntity<EstacionamentoResponseDto> getByRecibo(@PathVariable String recibo) {
        ClienteVaga clienteVaga = clienteVagaService.buscarPorRecibo(recibo);
        EstacionamentoResponseDto responseDto = ClienteVagaMapper.toDto(clienteVaga);
        return ResponseEntity.status(HttpStatus.OK).body(responseDto);
    }

    @Operation(
            summary = "Operação de check-out de veículo",
            description = "Requisição exige um Bearer Token. Acesso restrito a ADMIN",
            security = @SecurityRequirement(name = "security"),
            parameters = {
                    @Parameter(in = ParameterIn.PATH, name = "recibo", description = "Número do recibo gerado no check-in")
            },
            responses = {
                    @ApiResponse(responseCode = "200", description = "Check-out realizado com sucesso",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = EstacionamentoResponseDto.class))),
                    @ApiResponse(responseCode = "401", description = "Token inválido ou expirado",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = ErrorMessage.class))),
                    @ApiResponse(responseCode = "403", description = "Usuário sem permissão de acesso",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = ErrorMessage.class))),
                    @ApiResponse(responseCode = "404", description = "Número de recibo não encontrado, ou já realizou o check-out",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = ErrorMessage.class)))
            }
    )
    @PutMapping("/check-out/{recibo}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<EstacionamentoResponseDto> checkOut(@PathVariable String recibo) {
        ClienteVaga clienteVaga = estacionamentoService.checkOut(recibo);
        EstacionamentoResponseDto responseDto = ClienteVagaMapper.toDto(clienteVaga);
        return ResponseEntity.status(HttpStatus.OK).body(responseDto);
    }

    @Operation(
            summary = "Buscar todos estacionamentos do cliente pelo CPF",
            description = "Requisição exige um Bearer Token. Acesso restrito a ADMIN",
            security = @SecurityRequirement(name = "security"),
            parameters = {
                    @Parameter(in = ParameterIn.PATH, name = "cpf", description = "Número do CPF do cliente",
                            required = true),
                    @Parameter(in = ParameterIn.QUERY, name = "page", description = "Representa a página retornada",
                            content = @Content(schema = @Schema(type = "integer", defaultValue = "0"))),
                    @Parameter(in = ParameterIn.QUERY, name = "size", description = "Representa o total de registros por página",
                            content = @Content(schema = @Schema(type = "integer", defaultValue = "5"))),
                    @Parameter(in = ParameterIn.QUERY, name = "sort", description = "Campo(s) para ordenação",
                            content = @Content(schema = @Schema(type = "string", defaultValue = "dataEntrada,asc")),
                            hidden = true)
            },
            responses = {
                    @ApiResponse(responseCode = "200", description = "Estacionamento encontrado com sucesso",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = PageableDto.class))),
                    @ApiResponse(responseCode = "401", description = "Token inválido ou expirado",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = ErrorMessage.class))),
                    @ApiResponse(responseCode = "403", description = "Usuário sem permissão de acesso",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = ErrorMessage.class)))
            }
    )
    @GetMapping("/cpf/{cpf}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<PageableDto> getAllEstacionamentosPorCpf(@PathVariable String cpf, @Parameter(hidden = true)
                                                                   @PageableDefault(size = 5, sort = {"dataEntrada"},
                                                                   direction = Sort.Direction.ASC) Pageable pageable) {
        Page<ClienteVagaProjection> projection = clienteVagaService.buscarTodosPorClienteCpf(cpf, pageable);
        PageableDto pageableDto = PageableMapper.toDto(projection);
        return ResponseEntity.status(HttpStatus.OK).body(pageableDto);
    }
}
