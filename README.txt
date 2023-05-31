To launch code:
follow tutorial in this article: https://libgdx.com/wiki/start/import-and-running
(find your ide there)

Also, it is important to specify path to your file with values(default path from source root is specified by default,
 but it doesn't work for everyone(for me it works only with absolute path for some reason))
 You can specify path in the constructor of SimulationLogic class(DataReader dataReader = new DataReader("your_path_goes_here"))

 If you want to test different solvers just comment those that you don't need(only one at the same time can run)
  in RUNNING block in SimulationLogic

  Game will stop automatically after 1 year(Not for rk4 solver as it travels in time approx. 20 faster than it should for some reason,
  so for it you can try to divide STEPSIZE approx by 20 to get more or less normal speed(still not the same as it should))
