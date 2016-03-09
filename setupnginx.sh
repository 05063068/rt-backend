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
       aws elb create-load-balancer-listeners --load-balancer-name "$lb" --listeners "Protocol=HTTP,LoadBalancerPort=80,InstanceProtocol=HTTP,InstancePort=81" --region "$REGION"
       # add egress rule for nginx access
       lbsecurity_group = $(aws elb describe-load-balancers --load-balancer-names "$lb" --region "$REGION" --query 'LoadBalancerDescriptions[0].SecurityGroups[0]' --output text)
       aws ec2 authorize-security-group-egress --group-id "$lbsecurity_group" --ip-permissions '[{"IpProtocol": "tcp", "FromPort": 81, "ToPort": 81, "IpRanges": [{"CidrIp": "0.0.0.0/0"}]}]' --region "$REGION"
       # add ingress rule for nginx access
       launchcfgname=$(aws elasticbeanstalk describe-environment-resources  --environment-name "$BEANSTALK_ENVIRONMENT" --region "$REGION" --query EnvironmentResources.LaunchConfigurations[*].Name --output text)
       lcsecurity=$(aws autoscaling describe-launch-configurations --launch-configuration-names "$launchcfgname" --region "$REGION" --query 'LaunchConfigurations[0].SecurityGroups[1]' --output text)
       lc_security_id=$(aws ec2 describe-security-groups --group-names "$lcsecurity" --region "$REGION" --query 'SecurityGroups[0].GroupId' --output text)
       aws ec2 authorize-security-group-ingress --group-id "$lc_security_id" --protocol tcp --port 81 --source-group "$lbsecurity_group" --region "$REGION"
done

