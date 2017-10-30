# Java Concurrency


![overview](https://user-images.githubusercontent.com/4140597/31798079-b3dea3a2-b529-11e7-996f-289a6efe28e1.png)


## Synchronizers 

![syn](https://user-images.githubusercontent.com/4140597/31811841-e7cba3fa-b578-11e7-8f11-1ddf8159d68e.png)


### Sempaphore 

#### Example 
Imagine we have to park with a five parking space. When the parking space is full, newly arrived car can park after car from parking space will leave the parking.

![sempaphore](https://user-images.githubusercontent.com/4140597/31797580-d18a26fe-b526-11e7-8a97-21f5f6df8cc3.gif)

[Documentation](https://docs.oracle.com/javase/8/docs/api/java/util/concurrent/Semaphore.html)


### CountDownLatch 

#### Example 

Imagine we have car racing before we can begin, following conditions should meet: 
* All five cars should be on start line 
* The command "Prepare!" should be done
* The command "Ready!" should be done
* The command "Go!" should be done

It's essential that all five cars start at the same time. 

![countdownlatch](https://user-images.githubusercontent.com/4140597/31797648-50b41c82-b527-11e7-9e06-f216e3408e00.gif)

[Documentation](https://docs.oracle.com/javase/8/docs/api/java/util/concurrent/CountDownLatch.html)


### CyclicBarrier

#### Example 
Imagine we have a ferry that transport cars. The boat can transport only three vehicles at the same time, and we can carry cars just after on a ferry we had three vehicles. 

![cyclicbarrier](https://user-images.githubusercontent.com/4140597/31797681-81f57ca0-b527-11e7-83b6-5933e7627fed.gif)

[Documentation](https://docs.oracle.com/javase/8/docs/api/java/util/concurrent/CyclicBarrier.html)


### Exchanger

#### Example 

Imagine we have two tracks. The first one is going from A to D, and the second one is going from B to C. The roads AD and BC is crossing at E. From A and B we should dispatch envelopes to C and D, to do that, two tracks should meet at E and exchange envelopes.

![exchanger](https://user-images.githubusercontent.com/4140597/31797744-d51630aa-b527-11e7-93ad-0772562af397.gif)

[Documentation](https://docs.oracle.com/javase/8/docs/api/java/util/concurrent/Exchanger.html)

### Phaser

#### Example
Imagine we have five bus stops. At the first four people can wait for a bus. The bus starts a journey and stops on all the bus stops, after fifth bus stop it's going to bus depot. We should: 
* pick the people 
* drop off the people  

![phaser](https://user-images.githubusercontent.com/4140597/31797763-f9d50948-b527-11e7-8066-a320dba56d8c.gif)

[Documentation](https://docs.oracle.com/javase/8/docs/api/java/util/concurrent/Phaser.html)
