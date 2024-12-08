package com.example.service;


import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.model.Usuario;
import com.example.repository.UsuarioRepository;


@Service
public class UsuarioServiceImpl implements UsuarioService{

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Override
    public List<Usuario> getAllUsers() {
        return usuarioRepository.findAll();
    }

    @Override
    public Optional<Usuario> getUsuarioById(Long id) {
        return usuarioRepository.findById(id);
    }
    @Override
    public Usuario crearUsuario(Usuario usuario) {

        if (usuario.getBirthdate().isBefore(LocalDate.of(1900, 1, 1))) {
            throw new IllegalArgumentException("La fecha de nacimiento debe ser posterior a 1900");
        }
        if (usuario.getBirthdate().isAfter(LocalDate.now())) {
            throw new IllegalArgumentException("La fecha de nacimiento debe ser en el pasado");
        }
        return usuarioRepository.save(usuario);
    }

    @Override
    public Usuario modificarUsuario(Long id, Usuario usuario) {
        return usuarioRepository
        .findById(id)
        .map(user -> {
          user.setNombre(usuario.getNombre());
          user.setPassword(usuario.getPassword());
          user.setBirthdate(usuario.getBirthdate());
          user.setRol(usuario.getRol());
          return usuarioRepository.save(user);
        })
        .orElseThrow(() -> new RuntimeException("No se encontró el usuario " + id));
    }
    public void borrarUsuario(Long id){
        if (usuarioRepository.existsById(id)) {
            usuarioRepository.deleteById(id);
          } else {
            throw new RuntimeException("No se encontró el usuario: " + id);
          }
    }
    @Override
    public Optional<Usuario> autenticarUsuario(String email, String password) {
        return usuarioRepository.findByEmailAndPassword(email, password);
    }
    
}
