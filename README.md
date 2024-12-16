# Documentación del Proyecto: **Gestion de clientes**

#### **Descripción General**
Integra funcionalidades como validación de usuarios en un servicio externo y operaciones CRUD para los clientes en una base de datos H2.

El proyecto se encuentra separado por dos tipos de APIs UX y Negocio.

---

#### **Requerimientos Funcionales**

1. **Registrar clientes**:
    - Consume el servicio REST público `https://gorest.co.in/` para validar si el usuario existe.
    - Si el "name" o "email" existe, el status se regitrara como ***exists***, o caso contrario como ***active***.
    - Se valida el "email" para evitar duplicación de datos.

2. **Buscar clientes**:
    - Busqueda de todos los usuarios regitrado.
    - Busqueda de usuarios por "name" o "email", 

3. **Actualizar**:
    - El sistema actualiza el cliente por ID.

4. **Eliminar**:
    - El sistema permite eliminar por ID.

---

#### **Requerimientos No Funcionales**
- **Lenguaje**: Java 17.
- **Framework**: Spring Boot 3.
- **Base de Datos**: H2 (persistencia en memoria).
- **Programación Reactiva**: WebFlux y R2DBC.
- **Documentación API**: Swagger (integrado con SpringDoc OpenAPI).
- **Pruebas**: Postman.
- **Test Unitarios** cobertura mayor 65%%
- **Manejo de Excepciones**: Control centralizado de errores mediante `@ControllerAdvice`.
- **Swagger**: Documentacion con OpenAPI http://localhost:8081/v3/api-docs

---

#### **Instalación**
1. Clonar el repositorio:
   ```bash
   git clone <repositorio>
   cd <repositorio>
   ```

2. Compilar el proyecto:
   ```bash
   mvn clean install
   ```

3. Ejecutar la aplicación:
   ```bash
   mvn spring-boot:run
   ```

4. Acceder a la aplicación:
    - OpenAPI / Swagger: [http://localhost:8081/v3/api-docs](http://localhost:8081/v3/api-docs)
    - Reporte cobertura: ***target/site/jacoco/index.html

---

#### **Estructura del Proyecto**
- **`controller`**:
    - Endpoints para las APIs.

- **`business`**:
    - Lógica de negocio.
  
- **`entity`**:
    - Representacion de las tablas de nuestra BD.

- **`repository`**:
    - Interfaz para el acceso a datos reactivos con R2DBC.

- **`mapper`**:
    - Utilitarios pra la transformación de los objectos con mapstruct.

- **`proxy`**:
    - Configuracion para consumo de servicios externos (WebClient).

- **`exception`**:
    - Manejo centralizado de excepciones con mensajes descriptivos para cada caso.

---

#### **API Endpoints**

1. ****API UX****
   - **POST (`/ux/api/customer`)**: Registro de clientes.
   - **GET (`/ux/api/customer`)**: Lista los clientes registrados.
   - **GET (`/ux/api/customer/by?email=...`)**: (QueryParam) Busca un cliente por "name" o "email".
   - **PUT (`/ux/api/customer/{id}`)**: Actualiza un cliente por ID.
   - **DELETE (`/ux/api/customer`)**: Elimina un clientes por ID.
   - **GET (`/v3/api-docs`)**: Muestra el contrato Swagger/OpenAPI.

1. **API Soporte**
    - **POST (`/api/customer`)**: Registro de clientes.
    - **GET (`/api/customer`)**: Lista los clientes registrados.
    - **GET (`/api/customer/by?email=...`)**: (QueryParam) Busca un cliente por "name" o "email".
    - **PUT (`/api/customer/{id}`)**: Actualiza un cliente por ID.
    - **DELETE (`/api/customer`)**: Elimina un clientes por ID.

---

#### **Pruebas UX**
- **Registro de usuario (Postman)**:
   ```bash
  curl --location 'http://localhost:8081/ux/api/customer' \
    --header 'Content-Type: application/json' \
    --data-raw '{
    "name": "Daevi Shukla",
    "address": "Surquillo",
    "phone": "999999999",
    "email": "shukla_daevi@gerhold-krajcik.test",
    "gender": "male"
    }'
   ```

- **Buscar por email (Postman)**:
   ```bash
  curl --location 'http://localhost:8081/ux/api/customer/by?email=shukla_daevi%40gerhold-krajcik.test'
   ```

- **Actualizar**:
   ```bash
   curl --location --request PUT 'http://localhost:8081/ux/api/customer/1' \
    --header 'Content-Type: application/json' \
    --data-raw '{
    "name": "Agrata Jha",
    "address": "Surquillo",
    "phone": "999999999",
    "email": "jha_agrata@rowe.test",
    "gender": "male",
    "status": "inactive"
    }'
   ```

- **Eliminar (Postman)**:
   ```bash
  curl --location --request DELETE 'http://localhost:8081/ux/api/customer/1'
   ```

---

