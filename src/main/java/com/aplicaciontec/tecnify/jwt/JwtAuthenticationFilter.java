package com.aplicaciontec.tecnify.jwt;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component                                      //el filtro lo ejecutará una vez por cada solicitud http
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtService jwtService;
    private final UserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        final String token = getTokenFromRequest(request);  // Obtener el token
        String correo;   // Aquí almacenaremos el correo extraído del token



        if (token == null){
            filterChain.doFilter(request, response);

            return;
        }

        correo = jwtService.getUsernameFromToken(token);  // Obtener el correo del token
        System.out.println(correo);
        if (correo != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            // Si el correo es válido y no está en el SecurityContext
            UserDetails userDetails = userDetailsService.loadUserByUsername(correo);  // Cargar los detalles del usuario usando el correo

            // Validar si el token es válido
            if (jwtService.isTokenValid(token, userDetails)) {
                // Si el token es válido, crear la autenticación
                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                        userDetails,
                        null,
                        userDetails.getAuthorities()
                );
                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                // Establecer la autenticación en el contexto
                SecurityContextHolder.getContext().setAuthentication(authToken);
            }
        }

        // Continuar con la cadena de filtros
        filterChain.doFilter(request, response);
    }

    // Encuentra el token del request y lo devuelve
    private String getTokenFromRequest(HttpServletRequest request) {
        final String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);

        if (StringUtils.hasText(authHeader) && authHeader.startsWith("Bearer ")) {
            return authHeader.substring(7);  // Retornar el token sin el prefijo "Bearer "
        }
        return null;
    }
}
