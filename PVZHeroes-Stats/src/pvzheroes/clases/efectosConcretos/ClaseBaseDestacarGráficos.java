package pvzheroes.clases.efectosConcretos;

public interface ClaseBaseDestacarGráficos {
    public final double OPACIDAD_NORMAL = 0.9;
    public final double OPACIDAD_DESTACADO = 1;
    public final double OPACIDAD_BAJA = 0.2;

    public abstract void FijarOpacidadNormal();

    public abstract void AñadirHandlerEntrada();

    public abstract void AñadirHandlerSalida();
}
