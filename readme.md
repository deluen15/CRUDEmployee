# Employer Service

The Employer application is a component that provides CRUD (Create, Read, Update, Delete) operations for the `Employer` entity. It is implemented as a service
class
in the `com.example.employer.service` package and interacts with a MongoDB database. Additionally, it includes integration with the ELK (Elasticsearch,
Logstash, Kibana) stack for visualizing logs in Kibana. The service also utilizes the OpenAPI Swagger generator by Maven plugin to automatically generate API
documentation.

## CRUD Operations

The Employer Service supports the following CRUD operations:

### Create (Save) Employer

The `saveEmployee` method is responsible for saving a new employer to the MongoDB database. It takes an `EmployerDTO` object as input, which represents the
employer to be saved. If the employer already has an ID, it uses that ID; otherwise, it generates a new UUID as the ID. The method maps the `EmployerDTO` to
an `Employer` object using the `EmployerMapper`, sets the ID, and saves it to the database. The action is logged using the log level `info`, along with the
saved employer's ID.

### Read (Retrieve) Employers

The `findAllEmployers` method retrieves all employers from the MongoDB database. It uses the `employerRepository.findAll()` method to fetch the employers and
maps them to a list of `EmployerDTO` objects using the `EmployerMapper`. The method also logs the number of employers found using the log level `debug`.

The `getEmployerByID` method retrieves a specific employer from the database based on the provided ID. It uses the `employerRepository.findById(id)` method to
find the employer. If an employer with the specified ID is found, it is mapped to an `EmployerDTO` object using the `EmployerMapper` and returned. If no
employer is found, it throws an `EmployerNotFoundException` and logs a warning message with the ID using the log level `warn`.

### Update Employer

The `updateEmployer` method updates an existing employer in the MongoDB database. It takes an ID and an `EmployerDTO` object representing the updated employer
as input. First, it retrieves the existing employer from the database using the `employerRepository.findById(id)` method. If the employer is not found, it
throws an `EmployerNotFoundException`. Otherwise, it maps the properties of the `updatedEmployer` to the existing employer using the `EmployerMapper`, sets the
ID, and saves it to the database. The action is logged with the updated employer's ID using the log level `debug`.

### Delete Employer

The `deleteEmployerByID` method deletes an employer from the MongoDB database based on the provided ID. It uses the `employerRepository.deleteById(id)` method
to remove the employer with the specified ID from the database. The action is logged with the deleted employer's ID using the log level `debug`.

## ELK Stack Integration

The Employer Service integrates with the ELK (Elasticsearch, Logstash, Kibana) stack to enable centralized logging and log visualization in Kibana. The ELK
stack consists of the following components:

- Elasticsearch: Stores and indexes the logs generated by the service.
- Logstash: Collects, processes, and forwards the logs to Elasticsearch.
- Kibana: Provides a web interface for searching, analyzing, and visualizing the logs stored in Elasticsearch.

By integrating with the ELK stack, the service can send its log statements to Logstash, which parses and forwards them to Elasticsearch for indexing. Kibana can
then be used to explore and visualize the logs, enabling efficient log analysis and monitoring.

## Monitoring

This application uses Prometheus and Grafana for monitoring and visualizing its CRUD functionality. Prometheus is a powerful open-source toolkit for monitoring
and alerting, while Grafana is a feature-rich open-source platform for analytics and monitoring. In addition, this setup incorporates Loki and Promtail for
managing logs. By integrating these monitoring and logging tools into the Docker Compose environment, you can effectively monitor and analyze various metrics
from your CRUD application in real-time.

## OpenAPI Documentation Generation

The Employer Service utilizes the OpenAPI Swagger generator by Maven plugin to automatically generate API documentation. This documentation describes the
available endpoints, their input and output parameters, and any additional annotations provided for documenting the API. The plugin scans the service class and
generates the OpenAPI documentation based on the annotated methods and models. The generated documentation can be used to understand the service's API and can
be viewed using tools such as Swagger UI or integrated into other documentation systems.
