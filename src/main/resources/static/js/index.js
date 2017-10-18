/**
 * Created by Guo Zhaotong on 2017/10/9.
 */
var app = angular.module('myapp', ['ui.bootstrap']);
app.controller('menu', function ($scope, $http) {
    var tableObj
    var table = layui.table;
    var tableIns
    $scope.dangqianxuanze = "";
    var dangqianhangdata;

    $scope.isCollapsed = true;
    $scope.isCollapsedchildren = true;
    $scope.isCollapsedSun = true;
    $scope.cache1
    $scope.cache2
    $scope.cache3
    $scope.cache4

    $scope.save1 = function (x) {
        $scope.cache1 = x
        $scope.dangqianxuanze = $scope.cache1.name
    }
    $scope.save2 = function (x) {
        $scope.cache2 = x
        $scope.dangqianxuanze = $scope.cache1.name + "--" + $scope.cache2.name
    }
    $scope.save3 = function (x) {
        $scope.cache3 = x
        $scope.dangqianxuanze = $scope.cache1.name + "--" + $scope.cache2.name + "--" + $scope.cache3.name
    }
    $scope.save4 = function (x) {
        $scope.cache4 = x
        $scope.dangqianxuanze = $scope.cache1.name + "--" + $scope.cache2.name + "--" + $scope.cache3.name + "--" + $scope.cache4.name
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

        if ($scope.cache1 != null) {
            cache1_bianma = $scope.cache1.bianma
            cache1_name = $scope.cache1.name
            cache1_id = $scope.cache1.id
        }
        else {
            cache1_bianma = ""
            cache1_name = ""
            cache1_id = ""
        }
        if ($scope.cache2 != null) {
            cache2_bianma = $scope.cache2.bianma
            cache2_name = $scope.cache2.name
        }
        else {
            cache2_bianma = ""
            cache2_name = ""
        }
        if ($scope.cache3 != null) {
            cache3_bianma = $scope.cache3.bianma
            cache3_name = $scope.cache3.name
        }
        else {
            cache3_bianma = ""
            cache3_name = ""
        }
        if ($scope.cache4 != null) {
            cache4_bianma = $scope.cache4.bianma
            cache4_name = $scope.cache4.name
        }
        else {
            cache4_bianma = ""
            cache4_name = ""
        }

        $('#myModal').modal('toggle');
        $.ajax({
            type: "post",
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
            success: function (data) {
                tableObj.update({
                    firstName: data.firstName
                    , secondName: data.secondName
                    , thirdName: data.thirdName
                    , categoryName: data.categoryName
                });
                $scope.cache1 = null
                $scope.cache2 = null
                $scope.cache3 = null
                $scope.cache4 = null
                $scope.dangqianxuanze = ""

            }
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
                , {field: 'taggingDiseaseName', title: '描述', width: 250}
                , {field: 'categoryName', title: '大类', width: 250}
                , {field: 'firstName', title: '第一级', width: 250}
                , {field: 'secondName', title: '第二级', width: 250}
                , {field: 'thirdName', title: '第三级', width: 250}
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
                dangqianhangdata = data
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




