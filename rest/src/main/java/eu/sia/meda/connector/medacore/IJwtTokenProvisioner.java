package eu.sia.meda.connector.medacore;

/**
 * The Interface IJwtTokenProvisioner.
 */
public interface IJwtTokenProvisioner {
   
   /**
    * Manipulate token.
    *
    * @param configuration the configuration
    * @return the string
    */
   String manipulateToken(ArchMedaCoreConnectorConfigurationService.MedaCoreConfiguration configuration);
}
