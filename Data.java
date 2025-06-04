import java.time.LocalDate;

public class Data {
    private int dia;
    private int mes;
    private int ano;

    public Data(int dia, int mes, int ano) {
        this.dia = dia;
        this.mes = mes;
        this.ano = ano;
    }

    public int getDia() {
        return dia;
    }

    public int getMes() {
        return mes;
    }

    public int getAno() {
        return ano;
    }


    public boolean isAnterior(Data outra) {
        if (this.ano < outra.ano)
            return true;
        if (this.ano == outra.ano && this.mes < outra.mes)
            return true;
        if (this.ano == outra.ano && this.mes == outra.mes && this.dia < outra.dia)
            return true;
        return false;
    }


    public static Data hoje() {
        LocalDate agora = LocalDate.now();
        return new Data(agora.getDayOfMonth(), agora.getMonthValue(), agora.getYear());
    }

    @Override
    public String toString() {
        return String.format("%02d/%02d/%04d", dia, mes, ano);
    }
}
