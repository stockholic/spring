<script src="../../../../static/assets/jquery/jquery.min.js"></script> 
<script src="../../../../static/js/angular.min.js"></script> 


 <body ng-app='myApp' ng-controller='MainCtrl'>
    <h1>Hello Calcualtor</h1>
    <form>
  <div class="form-group">
    <label for="ip1">input 1</label>
    <input type="number" class="form-control" id="ip1" ng-model='x' placeholder="Enter number">
  </div>
  <div class="form-group">
    <label for="ip2">input 2</label>
    <input type="number" class="form-control" id="ip2" ng-model='y' placeholder="Enter number">
  </div>
  <div class="form-group">
    <label for="ip3">result</label>
    <input type="number" class="form-control" id="ip3" ng-model='r' disabled >
  </div>
    </form>
  </body>


<script>


var myApp = angular.module('myApp', []);

myApp.controller('MainCtrl', ['$scope', function ($scope) {

	 $scope.x = 0;
     $scope.y = 0;
         
	$scope.$watch("x*y", function (result) {
		console.log('new result',result);
		$scope.r = result;
	});

}]);

</script>