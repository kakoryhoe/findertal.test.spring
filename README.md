Endpoint pour authentification:
curl -X POST -H 'Content-type: application/json' -d '{"username": "admin","password": "admin"}' http://localhost:8080/api/authenticate

Endpoint pour avoir la liste des voitures sans commentaires:
curl -X GET http://localhost:8080/api/cars

Endpoint pour avoir les commentaires d'une voiture, authentification requise:
curl -X GET -H 'Authorization: Bearer {token}' http://localhost:8080/api/car/{carId}/comments

Endpoint pour ajouter un commentaire à une voiture:
curl -X POST -H 'Authorization: Bearer {token}' http://localhost:8080/api/car/{carId}/comment
