To launch code:
follow tutorial in this article: https://libgdx.com/wiki/start/import-and-running
(find your ide there)

Also, it is important to specify path to your file with values(default path from source root is specified by default,
 but it doesn't work for everyone(for me, it works only with absolute path for some reason))
 You can specify path in the constructor of SimulationLogic class(DataReader dataReader = new DataReader("your_path_for_values_goes_here"))

 If you want to test different solvers just comment those that you don't need(only one at the same time can run)
  in RUNNING block in SimulationLogic

Flight scene:
  Flight scene starts from launching probe(rocket) from Earth

  After reaching Titans orbit game scene will switch to landing. Note that landing is not connected with flight scene logically,
    even though transition happens smooth.

Landing scene:
  Landing is a completely different scene which can exist independently of flight scene.

  Before landing scene starts it executes pre-calculation process which calculates and saves to the Coordinates.txt all states of probe during
    the landing process.

  Landing starts from specified location and uses pre-calculated states to show how landing module would land on Titan.

  After reaching world zero-point(reaching latest record in Coordinates.txt file) simulation will pause. All logical processes
    at this point are going to end.

JavaDoc:
  If you want to read JavaDoc for this project you can find it in "JavaDoc/" folder. Just open overview-summary.html with
    your browser

JUnit tests:
  We weren't able to properly build tests in this project, so we created a separate project for running tests for Wind class.
  You can find this project in the main archive.
  Note that only tests for Wind are presented at this point.
