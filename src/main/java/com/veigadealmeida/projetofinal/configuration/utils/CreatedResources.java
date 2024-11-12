package com.veigadealmeida.projetofinal.configuration.utils;

import java.net.URI;
import java.util.List;

public record CreatedResources<T>(List<T> resources, List<URI> uris) {
}
