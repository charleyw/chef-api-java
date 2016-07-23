Chef java api
========

This project is a java implemented chef api client.

## Usage

    ChefApiClient cac = new ChefApiClient("username", "path to pem key", "chef server url");
    cac.get("/nodes").execute();

    # /nodes
    cac.get("/nodes").execute();
    cac.post("/nodes").body("req body").execute();
    cac.delete("/nodes").execute();
    cac.put("/nodes").body("req body").execute();

## Liscense

This code is distributed under the **MIT** license.
