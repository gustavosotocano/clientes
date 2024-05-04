package com.gml.enums;


public enum TipoDocumento {
    C("Cedula ciudadania"), P("Pasaporte");
    private String tipoDocumento;

    TipoDocumento(String tipoDocumento) {
        this.tipoDocumento = tipoDocumento;
    }

    public String getTipoDocumento() {
        return tipoDocumento;
    }
}
