<h1> ⚙️ Elevator management system</h1>
<p> Application presenting a simulation of an elevator management system. </p>


<h3> ▫️ Algorithm: </h3>
<ul>
  <li> The algorithm I designed is a greedy algorithm. When a request is sent, the algorithm searches for the elevator that is closest to the floor from which the request comes. </li>
  <li> The elevator can be in an IDLE state (not moving) or directed up/down.</li>
  <li> When it comes to elevators that move up and down, it is necessary for the floor from which the request is made to be located along their route. </li>
  <li> The elevator that is IDLE and closest to the floor from which the request is made is selected if none of the moving elevators have that floor on their route.</li>
  <li> If there are no more stops in the direction in which the elevator is moving, it changes direction (if there are stops in the opposite direction) or becomes IDLE (if there are no more stops).</li>
</ul>



<h3> ▫️ Technologies used: </h3>
<ul>
<li>Java 17</li>
<li>JavaFX</li>
<li>Gradle</li>
</ul>

<h3> ▫️ Application presentation: </h3> 
<p> The application will first ask for data. Remember that the number of elevators must be less than or equal to 16. Otherwise, the program will throw an IllegalArgumentException. </p>
<img src="/readme/start.gif">

<p> The application allows for easy monitoring of the situation. At the beginning, each elevator is in the IDLE state and located on the 0th floor. On the right side, there is a sidebar that describes the current status of each elevator. </p>
<img src="/readme/scrolling.gif">

<h3> ▫️How passengers call the elevator?</h3>
<p> The desired floor to which the passenger wants to go can be entered in the TextField located under the red button. For example, if the passenger is on the 3rd floor and wants to go to the 10th floor, they must enter 10 in the TextField corresponding to the floor number 3. Then, to call the elevator, the red button must be pressed.</p>
<img src="/readme/demo.gif">
