# crm-rest-cf

Cloud Native RESTfull API

##Testing steps

git clone https://github.com/ahmedmeid/crm-rest.git

export JWT_SECRET="3G778GvuRYdkjagLGRPGykjzcFREvhbnPT5NrqSSZGN9HUwenbLL7VV9cpDyxrwW"
export JWT_TOKEN_VALIDITY=3600000
export JWT_TOKEN_ISSUER=goodjobsolutions.com
export ALLOWED_CLIENT_ADDRESS=“http://127.0.0.1:3000”

mvn clean install spring-boot:repackage -P cloudfoundry

cf api <Cloud Foundry API URL>

cf login

cf create-service postgresql-db trial postgres-instance

cf push
