package derfe.crypto.lab.service.consumer;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

// Indica a JPA/Hibernate que esta clase representa
// una entidad que será almacenada en la base de datos.
@Entity

// Define el nombre de la tabla asociada a esta entidad.
@Table(name = "consumers")
public class Consumer {

    // Identificador único del consumidor dentro de la base de datos.
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Nombre único que identifica al sistema o aplicación consumidora.
    @Column(name = "name", nullable = false, unique = true)
    private String name;

    // Indica si el consumidor tiene permitido utilizar la plataforma.
    @Column(name = "enabled", nullable = false)
    private boolean enabled = true;

    // Constructor vacío requerido por JPA.
    protected Consumer() {
    }

    // Constructor utilizado para crear un nuevo consumidor.
    public Consumer(String name) {
        this.name = name;
        this.enabled = true;
    }

    // Devuelve el nombre del consumidor.
    public String getName() {
        return name;
    }

    public void setName(String name) {
    this.name = name;
}

    // Devuelve el identificador único del consumidor.
    public Long getId() {
        return id;
    }

    // Indica si el consumidor está habilitado.
    public boolean isEnabled() {
        return enabled;
    }

    // Actualiza el estado del consumidor.
    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

}