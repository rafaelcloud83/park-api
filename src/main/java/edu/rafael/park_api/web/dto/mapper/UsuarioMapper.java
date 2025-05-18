package edu.rafael.park_api.web.dto.mapper;

import edu.rafael.park_api.entity.Usuario;
import edu.rafael.park_api.web.dto.UsuarioCreateDto;
import edu.rafael.park_api.web.dto.UsuarioResponseDto;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;

import java.util.List;
import java.util.stream.Collectors;

public class UsuarioMapper {

    public static Usuario toUsuario(UsuarioCreateDto createDto){
        return new ModelMapper().map(createDto, Usuario.class);
    }

    public static UsuarioResponseDto toDto(Usuario usuario){
        String role = usuario.getRole().name().substring("ROLE_".length());
        ModelMapper mapperMain = new ModelMapper();
        TypeMap<Usuario, UsuarioResponseDto> props = mapperMain.createTypeMap(Usuario.class, UsuarioResponseDto.class);
        props.addMappings(
                mapper -> mapper.map(
                        src -> role, UsuarioResponseDto::setRole
                )
        );
        return mapperMain.map(usuario, UsuarioResponseDto.class);
    }

    public static List<UsuarioResponseDto> toListDto(List<Usuario> usuarios){
        return usuarios.stream().map(user -> toDto(user)).collect(Collectors.toList());
    }
}
