package ittp.ittp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CyclicBarrier;

@SpringBootApplication
public class IttpApplication {

	public static void main(String[] args) {

		SpringApplication.run(IttpApplication.class, args);

		final int numberOfScenes = 3;
		final CyclicBarrier barrier = new CyclicBarrier(6, () -> System.out.println("Next scene..."));

		Thread monica = new Thread(new SitcomCharacter("Monica", List.of("Well, what's the job?", "Oh, wow, so you gonna be one of the 'healthy, healthy guys'?"), barrier, numberOfScenes));
		Thread chandler = new Thread(new SitcomCharacter("Chandler", List.of("Hey", "Are you wearing makeup?"), barrier, numberOfScenes));
		Thread joey = new Thread(new SitcomCharacter("Joey", List.of("Hey hey", "Yes, I am."), barrier, numberOfScenes));
		Thread phoebe = new Thread(new SitcomCharacter("Phoebe", List.of("Hey.", "What where you modeling for?"), barrier, numberOfScenes));
		Thread rachel = new Thread(new SitcomCharacter("Rachel", List.of("Assistant buyer", "I would be stopping for a living"), barrier, numberOfScenes));
		Thread ross = new Thread(new SitcomCharacter("Ross", List.of("Hey", "Hey hey"), barrier, numberOfScenes));

		chandler.start();
		joey.start();
		monica.start();
		phoebe.start();
		rachel.start();
		ross.start();
	}
}

class SitcomCharacter implements Runnable {

	private final String name;
	private final List<String> lines;
	private final CyclicBarrier barrier;
	private final int numberOfScenes;

	public SitcomCharacter(String name, List<String> lines, CyclicBarrier barrier, int numberOfScenes) {

		this.name = name;
		this.lines = new ArrayList<>(lines);
		this.barrier = barrier;
		this.numberOfScenes = numberOfScenes;
	}

	@Override
	public void run() {

		for (int scene = 0; scene < numberOfScenes; scene++) {

			Collections.shuffle(this.lines);

			for (int lineIndex = 0; lineIndex < 2; lineIndex++) {

				try {

					Thread.sleep((long) (Math.random() * 1000));
					System.out.println(name + ": " + lines.get(lineIndex));

				} catch (InterruptedException e) {

					Thread.currentThread().interrupt();

					return;
				}
			}

			try {

				barrier.await();
			}
			catch (InterruptedException | java.util.concurrent.BrokenBarrierException e) {

				Thread.currentThread().interrupt();

				return;
			}
		}
	}
}