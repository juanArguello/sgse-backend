package com.sgse.configuration;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.sgse.entities.Usuario;

/**
 * @author Juan Carlos Arguello Ortiz
 * @version 1.0
 */
public class UserDetailsImpl implements UserDetails {

	private static final long serialVersionUID = 1L;

	private final Usuario usuario;
	private String username;
	private String password;
	private boolean accountNonExpired;
	private boolean accountNonLocked;
	private boolean credentialsNonExpired;
	private boolean enabled;
	private List<GrantedAuthority> authorities;

	public UserDetailsImpl(Usuario usuario) {
		this.usuario = usuario;
		this.username = usuario.getUsername();
		this.password = usuario.getPassword();
		this.accountNonExpired = usuario.getEnabled();
		this.accountNonLocked = usuario.getEnabled();
		this.credentialsNonExpired = usuario.getEnabled();
		this.enabled = usuario.getEnabled();
		this.authorities = createAuthorities();
	}

	public UserDetailsImpl(String username, String password, boolean accountNonExpired, boolean accountNonLocked,
			boolean credentialsNonExpired, boolean enabled, List<GrantedAuthority> authorities) {
		this.username = username;
		this.password = password;
		this.accountNonExpired = accountNonExpired;
		this.accountNonLocked = accountNonLocked;
		this.credentialsNonExpired = credentialsNonExpired;
		this.enabled = enabled;
		this.authorities = authorities;
		this.usuario = new Usuario();
	}

	public UserDetailsImpl(String username, String password, List<GrantedAuthority> authorities) {
		this.username = username;
		this.password = password;
		this.authorities = authorities;
		this.accountNonExpired = true;
		this.accountNonLocked = true;
		this.credentialsNonExpired = true;
		this.enabled = true;
		this.usuario = new Usuario();
	}

	public static UserDetailsImpl buildUser(Usuario usuario) {
		List<GrantedAuthority> authorities = new ArrayList<>();
		authorities.add(new SimpleGrantedAuthority("ROLE_" + usuario.getIdRol().getNombre()));
		authorities.addAll(usuario.getIdRol().getPermisosList().stream()
				.map(permiso -> new SimpleGrantedAuthority(permiso.getNombre())).collect(Collectors.toList()));

		return new UserDetailsImpl(usuario.getUsername(), usuario.getPassword(), usuario.getEnabled(),
				usuario.getEnabled(), usuario.getEnabled(), usuario.getEnabled(), authorities);
	}

	private List<GrantedAuthority> createAuthorities() {
		List<GrantedAuthority> authorities = new ArrayList<>();
		// authorities.add(new SimpleGrantedAuthority(usuario.getIdRol().getNombre()));
		authorities.addAll(usuario.getIdRol().getPermisosList().stream()
				.map(permiso -> new SimpleGrantedAuthority(permiso.getNombre())).collect(Collectors.toList()));
		return authorities;
	}

	public Usuario getUsuario() {
		return this.usuario;
	}

	public String getRol() {
		return usuario.getIdRol().getNombre();
	}

	public List<String> getPermisos() {
		List<String> permisos = this.authorities.stream().map(item -> item.getAuthority()).collect(Collectors.toList());
		return permisos;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return this.authorities;
	}

	@Override
	public String getPassword() {
		return this.password;
	}

	@Override
	public String getUsername() {
		return this.username;
	}

	@Override
	public boolean isAccountNonExpired() {
		return this.accountNonExpired;
	}

	@Override
	public boolean isAccountNonLocked() {
		return this.accountNonLocked;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return this.credentialsNonExpired;
	}

	@Override
	public boolean isEnabled() {
		return this.enabled;
	}
}
