# Lab Service WSDL

Laboratorio para el desarrollo de servicios criptográficos y de certificados mediante Java y Spring Boot.

El objetivo es construir la solución de forma desacoplada para permitir la integración progresiva con infraestructura de laboratorio y posteriormente con componentes reales como HSM, PKI y TSA.

## Requisitos de desarrollo

- Java 21
- Docker Desktop
- Docker Compose
- Git

No es necesario instalar Maven globalmente. El proyecto utiliza Maven Wrapper.

## Preparación del entorno

Clonar el repositorio y entrar al proyecto.

Crear el archivo local de variables de entorno a partir de la plantilla:

Windows PowerShell:

```powershell
Copy-Item .env.example .env