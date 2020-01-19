# poc-crm-assets

## local mongo

`docker pull mongo`


## build image
`docker image build -t poc-asset .`


## run container
`docker container run --env MONGO_URL=mongodb://admin:admdev@ec2-18-217-111-150.us-east-2.compute.amazonaws.com:27017/admin -d -p 8082:8082 poc-asset`

## ENV

local dev : 

`MONGO_URL=mongodb://localhost:27017/admin`