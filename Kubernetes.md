POD - single or group of containers 
 each pod has one ip address

Node : pods wrapped inside node 
a node is single or multiple nodes

Clusters : a single or group of nodes.

K8s --> Master or worker nodes  (real computer or virtual machines)

Replication / Replica sets : maintain copy of pods in order to maintain quality attribute or non functional attribute : availability


Service : instead of hard coding host name with ip addresses , we can have a service DNS name. Service maintains static name to all the nodes.

Service can be used as loadbalancer.

Deployment : Kubernetes objects used for managing pods.  

Secrets & ConfigMap : store sensitive information and application config details.

ETCD: key-value database. stores, secrets and configmap data , max limit is 1mb.
