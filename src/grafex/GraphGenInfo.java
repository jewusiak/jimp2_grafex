package grafex;

public class GraphGenInfo {
    public enum Coherency {YES, NO, RANDOM}

    private final int rows;
    private final int columns;
    private final Coherency coherency;
    private final double weightBottom;
    private final double weightTop;

    public GraphGenInfo(int rows, int columns, Coherency coherency, double weightBottom, double weightTop) {
        this.rows = rows;
        this.columns = columns;
        this.coherency = coherency;
        this.weightBottom = weightBottom;
        this.weightTop = weightTop;
    }

    public int getRows() {
        return rows;
    }

    public int getColumns() {
        return columns;
    }

    public Coherency getCoherency() {
        return coherency;
    }

    public double getWeightBottom() {
        return weightBottom;
    }

    public double getWeightTop() {
        return weightTop;
    }


}
