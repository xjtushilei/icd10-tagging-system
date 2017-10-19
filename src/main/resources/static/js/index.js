/**
 * Created by Guo Zhaotong on 2017/10/9.
 */
var app = angular.module('myapp', ['ui.bootstrap']);
app.controller('menu', function ($scope, $http) {
    var tableObj
    var table = layui.table;
    var tableIns
    var dangqianhangdata;

    $scope.change1 = function () {
        if ($scope.di1 != null) {
            $scope.d2 = $scope.di1.list
            $scope.d3 = []
            $scope.d4 = []
        }
        else {
            $scope.d2 = []
            $scope.d3 = []
            $scope.d4 = []
        }

    }
    $scope.change2 = function () {
        if ($scope.di2 == null) {
            $scope.d4 = []
            $scope.d3 = []
        }
        else {
            $scope.d3 = $scope.di2.sanweima
            $scope.d4 = []
        }
    }
    $scope.change3 = function () {
        if ($scope.di3 == null) {
            $scope.d4 = []
        }
        else {
            $scope.d4 = $scope.di3.siweidaima
        }
    }
    $scope.saveinfo = function () {
        var cache1_id
        var cache1_name
        var cache1_bianma
        var cache2_name
        var cache2_bianma
        var cache3_name
        var cache3_bianma
        var cache4_name
        var cache4_bianma
        console.log($scope.di4)
        if ($scope.di1 != null && $scope.di1 != "") {
            cache1_bianma = $scope.di1.bianma
            cache1_name = $scope.di1.name
            cache1_id = $scope.di1.id
        }
        else {
            cache1_bianma = ""
            cache1_name = ""
            cache1_id = ""
        }
        if ($scope.di2 != null && $scope.di2 != "") {
            cache2_bianma = $scope.di2.bianma
            cache2_name = $scope.di2.name
        }
        else {
            cache2_bianma = ""
            cache2_name = ""
        }
        if ($scope.di3 != null && $scope.di3 != "") {
            cache3_bianma = $scope.di3.bianma
            cache3_name = $scope.di3.name
        }
        else {
            cache3_bianma = ""
            cache3_name = ""
        }
        if ($scope.di4 != null && $scope.di4 != "") {
            cache4_bianma = $scope.di4.bianma
            cache4_name = $scope.di4.name
        }
        else {
            cache4_bianma = ""
            cache4_name = ""
        }

        $('#myModal').modal('toggle');
        $http({
            method: 'post',
            url: appname + "/tagging/updateIcd10Info",
            data: {
                id: dangqianhangdata.id,
                taggingDiseaseCode: dangqianhangdata.taggingDiseaseCode,
                taggingDiseaseName: dangqianhangdata.taggingDiseaseName,
                categoryId: cache1_id,
                categoryCode: cache1_bianma,
                categoryName: cache1_name,
                firstName: cache2_name,
                firstCode: cache2_bianma,
                secondName: cache3_name,
                secondCode: cache3_bianma,
                thirdName: cache4_name,
                thirdCode: cache4_bianma,
            },
            headers: {
                'Content-Type': 'application/x-www-form-urlencoded'  //angularjs设置文件上传的content-type修改方式
            }, transformRequest: function (obj) {
                var str = [];
                for (var s in obj) {
                    str.push(encodeURIComponent(s) + "=" + encodeURIComponent(obj[s]));
                }
                return str.join("&");
            }
        }).success(
            function successCallback(data) {
                // data = response.data
                console.log(data)
                tableObj.update({
                    firstName: data.firstName
                    , secondName: data.secondName
                    , thirdName: data.thirdName
                    , categoryName: data.categoryName
                });
                $scope.di1 = ""
                $scope.di2 = ""
                $scope.di3 = ""
                $scope.di4 = ""

            });
    }

    var chapterDOM = $('#chapter');
    chapterDOM.hide()
    // $('#myModal').modal('toggle');

    $http.get(appname + "/tagging/getIcd10Info").success(
        function (json) {
            $('#wait').hide()
            $('#main').show()
            $scope.chapter = json;
            chapterDOM.show()
            chapterDOM.slimscroll({
                height: '550px'
            });
            console.log("列表加载成功")
        });
    $(document).ready(function () {



        //执行渲染
        tableIns = table.render({
            elem: '#table' //指定原始表格元素选择器（推荐id选择器）
            , cols: [[ //标题栏
                {field: 'id', title: 'id', width: 80}
                , {field: 'taggingDiseaseName', title: '描述', width: 230}
                , {field: 'categoryName', title: '第一级', width: 230}
                , {field: 'firstName', title: '第二级', width: 230}
                , {field: 'secondName', title: '第三级', width: 230}
                , {field: 'thirdName', title: '第四级', width: 230}
                , {fixed: 'right', width: 180, align: 'center', toolbar: '#barDemo'} //这里的toolbar值是模板元素的选择器
            ]]
            , url: '/tagging/getTagging/'
            , method: 'get' //如果无需自定义HTTP类型，可不加该参数
            , even: true //开启隔行背景
            , page: true

        });

        //监听工具条
        table.on('tool(test)', function (obj) { //注：tool是工具条事件名，test是table原始容器的属性 lay-filter="对应的值"

            var data = obj.data; //获得当前行数据
            tableObj = obj
            var layEvent = obj.event; //获得 lay-event 对应的值
            var tr = obj.tr; //获得当前行 tr 的DOM对象
            if (layEvent === 'tag') {
                $scope.RecommendList=[]
                $scope.di1 = ""
                $scope.di2 = ""
                $scope.di3 = ""
                $scope.di4 = ""
                dangqianhangdata = data
                $http.get(appname + "/tagging/Recommend?name="+data.taggingDiseaseName).success(
                    function (json) {
                        $scope.RecommendList=json
                    });
                $('#myModal').modal('toggle');

            } else if (layEvent === 'del') {
                dangqianhangdata = data
                $.ajax({
                    type: "post",
                    url: appname + "/tagging/updateIcd10Info",
                    data: {
                        id: dangqianhangdata.id,
                        taggingDiseaseCode: dangqianhangdata.taggingDiseaseCode,
                        taggingDiseaseName: dangqianhangdata.taggingDiseaseName,
                        categoryId: "",
                        categoryCode: "",
                        categoryName: "",
                        firstName: "",
                        firstCode: "",
                        secondName: "",
                        secondCode: "",
                        thirdName: "",
                        thirdCode: "",
                    },
                    success: function (data) {
                        obj.update({
                            firstName: ""
                            , secondName: ""
                            , thirdName: ""
                            , categoryName: ""
                        });

                    }
                });
            }
        });


    })

})




