package hro.infdta012.genetic;

public class IntegerIndividual extends Individual<Integer> {
	public IntegerIndividual(int bits) {
		super(bits);
	}

	protected IntegerIndividual(Integer value) {
		super(value);
	}

	@Override
	protected Integer randomize(int bits) {
		int b = 0;
		for(int p = 0; p < bits; p++) {
			b |= getRandomBit(p);
		}
		return b;
	}

	@Override
	public Individual<Integer> breed(Individual<Integer> parent) {
		int f = getValue();
		int p2 = parent.getValue();
		for(int i = 0; i < Integer.SIZE; i++) {
			if(Math.random() < 0.5) {
				f |= (p2 & (1 << i));
			}
		}
		return new IntegerIndividual(Integer.valueOf(f));
	}

	@Override
	public Individual<Integer> mutate(double mutationRate, int bits) {
		int value = getValue();
		for(int i = 0; i < bits; i++) {
			if(Math.random() < mutationRate) {
				value ^= (1 << i);
			}
		}
		return new IntegerIndividual(Integer.valueOf(value));
	}

	@Override
	public String toString() {
		return "<" + getValue() + ">";
	}
}
