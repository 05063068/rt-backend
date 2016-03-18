#!/bin/bash
BEANSTALK_ENVIRONMENT=$1

REDISPOLICY=LogstashUtility-SecurityGroup
REGION=us-west-2
DEPLOY_HOME=$(dirname "$0")

# shellcheck source=flx-thumbor-eb-dev.sh
#source "${DEPLOY_HOME}/${BEANSTALK_ENVIRONMENT}".sh

for lb in $(aws elasticbeanstalk describe-environment-resources  --environment-name "$BEANSTALK_ENVIRONMENT" --region "$REGION" --query 'EnvironmentResources.LoadBalancers[*].Name' --output text); do
       # setup nginx access
       echo setting up nginx access for "$lb"
       aws elb delete-load-balancer-listeners --load-balancer-name "$lb" --load-balancer-ports 80 --region "$REGION"
       aws elb create-load-balancer-listeners --load-balancer-name "$lb" --listeners "Protocol=HTTP,LoadBalancerPort=80,InstanceProtocol=HTTP,InstancePort=80" --region "$REGION"
done

