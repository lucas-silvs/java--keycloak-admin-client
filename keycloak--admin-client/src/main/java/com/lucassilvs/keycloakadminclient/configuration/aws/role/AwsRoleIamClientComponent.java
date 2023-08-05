package com.lucassilvs.keycloakadminclient.configuration.aws.role;

import com.lucassilvs.keycloakadminclient.configuration.exceptions.InfraestructureException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import software.amazon.awssdk.services.iam.IamClient;
import software.amazon.awssdk.services.iam.model.CreatePolicyRequest;
import software.amazon.awssdk.services.iam.model.CreatePolicyResponse;
import software.amazon.awssdk.services.iam.model.GetRoleResponse;

@Component
public class AwsRoleIamClientComponent {


    private final IamClient iamClient;
    
    private static final String POLICY_DOCUMENT_SECRET = "{ \"Version\": \"2012-10-17\", \"Statement\": [ {  \"Action\": [ \"secretsmanager:GetSecretValue\" ], \"Effect\": \"Allow\", \"Resource\": \"%s\" } ] }";

    @Autowired
    public AwsRoleIamClientComponent(IamClient iamClient) {
        this.iamClient = iamClient;
    }

    public GetRoleResponse getRoleByName(String roleName){
        return iamClient.getRole(r -> r.roleName(roleName));
    }

    public void addpolicySecretKeycloakCredentials(String roleName, String secretArn, String secretName){

        CreatePolicyResponse policyResponse = createPolicySecretManager(secretArn, secretName);

        try {
            iamClient.attachRolePolicy(r -> r.roleName(roleName).policyArn(policyResponse.policy().arn()));
        }catch (Exception e) {
            throw new InfraestructureException(String.format("Error attaching policy %s to role %s", policyResponse.policy().arn(), roleName), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    private CreatePolicyResponse createPolicySecretManager(String secretArn, String secretName) {
        CreatePolicyRequest policyRequest = CreatePolicyRequest.builder()
                .policyName(String.format("policy_secret_keycloak_credentials/%s", secretName))
                .description(String.format("Policy to allow access to secret %s", secretName))
                .policyDocument(String.format(POLICY_DOCUMENT_SECRET, secretArn))
                .build();

        try {
            return iamClient.createPolicy(policyRequest);
        } catch (Exception e) {
            throw new InfraestructureException(String.format("Error creating policy for secret %s", secretName), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
