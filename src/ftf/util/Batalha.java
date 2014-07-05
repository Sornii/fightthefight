package ftf.util;

import ftf.modelo.Criatura;

public class Batalha {

    private final Criatura Criatura1;

    private final Criatura Criatura2;

    public Batalha(Criatura Criatura1, Criatura Criatura2) {
        this.Criatura1 = Criatura1;
        this.Criatura2 = Criatura2;
    }
    
    public void Batalhar() {
        
        if(getCriatura1().getVidaAtual() <= 0 && getCriatura2().getVidaAtual() <= 0){
            return;
        }
        
        while (true) {    
            Atacar(getCriatura1(), getCriatura2());
            if (getCriatura2().getVidaAtual() > 0) {
                Atacar(getCriatura2(), getCriatura1());
                if (getCriatura1().getVidaAtual() <= 0) {
                    //atacante morreu
                    break;
                }
            } else {
                // defensor morreu
                break;
            }
        }
    }
    
    public void Atacar(Criatura a, Criatura b) {
        
    }

    public Criatura getCriatura1() {
        return Criatura1;
    }

    public Criatura getCriatura2() {
        return Criatura2;
    }

}
