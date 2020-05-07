package eu.sia.meda.jwt;

import java.io.Serializable;
import java.util.Map;
import java.util.Set;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class JwtAuthClaim implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2728427474961132167L;

	private String username;

	private Set<String> roles;

	private Map<String, String> authorizations;
	
}
