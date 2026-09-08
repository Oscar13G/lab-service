package derfe.crypto.lab.service.consumer;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

// Expone operaciones HTTP relacionadas con los consumidores.
@RestController
@RequestMapping("/consumers")
public class ConsumerController {

    private final ConsumerService consumerService;

    // Spring inyecta automáticamente el servicio.
    public ConsumerController(ConsumerService consumerService) {
        this.consumerService = consumerService;
    }

    // Crea un nuevo consumidor a partir de los datos recibidos.
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Consumer createConsumer(@RequestBody Consumer consumer) {

        return consumerService.createConsumer(consumer.getName());
    }

    // Devuelve todos los consumidores registrados.
    @GetMapping
    public List<Consumer> findAll() {
        return consumerService.findAll();
    }

    // Busca un consumidor por su nombre.
    // Si no existe, devuelve HTTP 404.
    @GetMapping("/{name}")
    public Consumer findByName(@PathVariable String name) {
        return consumerService.findByName(name)
                .orElseThrow(() ->
                        new ResponseStatusException(
                                HttpStatus.NOT_FOUND,
                                "Consumer no encontrado"
                        )
                );
    }

    // Habilita o deshabilita un consumidor existente.
    @PatchMapping("/{id}/status")
    public Consumer updateStatus(
            @PathVariable Long id,
            @RequestParam boolean enabled) {
        return consumerService.updateStatus(id, enabled)
                .orElseThrow(() ->
                        new ResponseStatusException(
                                HttpStatus.NOT_FOUND,
                                "Consumer no encontrado"
                        )
                );
    }
}