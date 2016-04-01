#!/bin/bash
BEANSTALK_ENVIRONMENT=$1

REDISPOLICY=LogstashUtility-SecurityGroup
REGION=us-west-2
DEPLOY_HOME=$(dirname "$0")

# shellcheck source=flx-thumbor-eb-dev.sh
#source "${DEPLOY_HOME}/${BEANSTALK_ENVIRONMENT}".sh

for lb in $(aws elasticbeanstalk describe-environment-resources  --environment-name "$BEANSTALK_ENVIRONMENT" --region "$REGION" --query 'EnvironmentResources.LoadBalancers[*].Name' --output text); do
       # setup health check
       echo setting up health check for "$lb"
       aws elb configure-health-check --load-balancer-name "$lb" --health-check Target=HTTP:80/movie/9,Interval=30,UnhealthyThreshold=2,HealthyThreshold=2,Timeout=3 --region "$REGION"
done

