package at.ac.tuwien.ase.service.dto;

import java.math.BigDecimal;
import java.math.BigInteger;

/**
 * Created by Michael on 22.06.2017.
 */
public class StatDTO {

    private String name;
    private BigInteger avg;
    private BigInteger min;
    private BigInteger max;
    private BigDecimal sum;

    public StatDTO(String name, BigInteger avg, BigInteger max, BigInteger min, BigDecimal sum) {
        this.name = name;
        this.avg = avg;
        this.min = min;
        this.max = max;
        this.sum = sum;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigInteger getAvg() {
        return avg;
    }

    public void setAvg(BigInteger avg) {
        this.avg = avg;
    }

    public BigInteger getMin() {
        return min;
    }

    public void setMin(BigInteger min) {
        this.min = min;
    }

    public BigInteger getMax() {
        return max;
    }

    public void setMax(BigInteger max) {
        this.max = max;
    }

    public BigDecimal getSum() {
        return sum;
    }

    public void setSum(BigDecimal sum) {
        this.sum = sum;
    }
}
