<h1> ⚙️ Elevator management system</h1>
<p> The program presents an algorithm that handles the elevator management system. It also provides a visualization of the designed algorithm and allows the user to try it out. </p>


<h3> ▫️ Algorithm: </h3>
<ul>
  <li> The algorithm I designed is a greedy algorithm. When a request is sent, the algorithm searches for the elevator that is closest to the floor from which the request comes. </li>
  <li> The elevator can be in an IDLE state (not moving) or directed up/down.</li>
  <li> When it comes to elevators that move up and down, it is necessary for the floor from which the request is made to be located along their route. </li>
  <li> The elevator that is IDLE and closest to the floor from which the request is made is selected if none of the moving elevators have that floor on their route.</li>
  <li> If there are no more stops in the direction in which the elevator is moving, it changes direction (if there are stops in the opposite direction) or becomes IDLE (if there are no more stops).</li>
</ul>

<h3> ▫️ Example: </h3>
<p> I believe that my algorithm is optimal and performs better than the "First Come, First Serve" algorithm. Let's consider a scenario where passengers are waiting on the first, fifth, and eighth floors, and we have only one elevator. If the requests are sent first by the passenger on the first floor, then the eighth floor, and finally the fifth floor, even though the elevator will pass the fifth floor where a passenger is waiting, it will not pick them up. It will have to return for them later, adding extra distance and time. In such a situation, my algorithm will pick up the person waiting on the fifth floor before person waiting on eight floor, because fifth floor will be on its route to eight floor.</p>

<h3> ▫️ Technologies used: </h3>
<ul>
<li>Java 17</li>
<li>JavaFX</li>
<li>Gradle</li>
</ul>

<h3> ▫️ Run a program: </h3> 
<p> To run the program you can execute the following commands:

```
./gradlew run
```

<p> or to avoid gradle command prompt use: </p>

```
./gradlew -q --console plain run
```

<h3> ▫️ Application presentation: </h3> 
<p> The application will first ask for data. Remember that the number of elevators must be less than or equal to 16. Otherwise, the program will throw an IllegalArgumentException. </p>
<img width="80%" src="/readme/start.gif">

<p> The application allows for easy monitoring of the situation. At the beginning, each elevator is in the IDLE state and located on the 0th floor. On the right side, there is a sidebar that describes the current status of each elevator. </p>
<img width="80%" src="/readme/scrolling.gif">

<h3> ▫️How passengers call the elevator?</h3>
<p> The desired floor to which the passenger wants to go can be entered in the TextField located under the red button. For example, if the passenger is on the 3rd floor and wants to go to the 10th floor, they must enter 10 in the TextField corresponding to the floor number 3. Then, to call the elevator, the red button must be pressed.</p>

<p> If a passenger enters a floor that does not exist (less than zero or greater than the highest floor), the program will throw an IllegalArgumentException.</p>
<img width="80%" src="/readme/demo.gif">

<p> Simple <a href="https://github.com/YoC00lig/Elevator-System/blob/main/src/test/java/elevators/ElevatorTest.java">tests</a> have been conducted to verify if the functions are working correctly. </p>
