package hro.infdta012.genetic;

public class LongIndividual extends Individual<Long> {
	public LongIndividual(int bits) {
		super(bits);
	}

	protected LongIndividual(Long value) {
		super(value);
	}

	@Override
	protected Long randomize(int bits) {
		long b = 0;
		for(int p = 0; p < bits; p++) {
			b |= getRandomBit(p);
		}
		return b;
	}

	@Override
	public Individual<Long> breed(Individual<Long> parent) {
		long f = getValue();
		long p2 = parent.getValue();
		for(int i = 0; i < Long.SIZE; i++) {
			if(Math.random() < 0.5) {
				f |= (p2 & (1L << i));
			}
		}
		return new LongIndividual(Long.valueOf(f));
	}

	@Override
	public Individual<Long> mutate(double mutationRate, int bits) {
		long value = getValue();
		for(int i = 0; i < bits; i++) {
			if(Math.random() < mutationRate) {
				value ^= (1L << i);
			}
		}
		return new LongIndividual(Long.valueOf(value));
	}

	@Override
	public String toString() {
		return "<" + getValue() + ">";
	}
}
