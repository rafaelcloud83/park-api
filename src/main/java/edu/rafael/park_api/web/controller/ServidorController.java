package edu.rafael.park_api.web.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@Tag(name = "00 - Status Servidor", description = "Recurso para verificar o status do servidor")
@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping()
public class ServidorController {
    @Operation(
            summary = "Verificar se o servidor está ativo",
            description = "Recurso para verificar se o servidor está ativo",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Servidor ativo"),
                    @ApiResponse(responseCode = "500", description = "Erro interno do servidor",
                        content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Exception.class)))
            }
    )
    @GetMapping(value = "/api/v1", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> servidorOK() {
        try {
            Map<String, String> resposta = new HashMap<>();
            resposta.put("mensagem", "Servidor OK");
            return ResponseEntity.ok(resposta);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }
}
