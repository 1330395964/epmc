/**
 * 通用js方法封装处理
 * Copyright (c) 2019 epmc
 */

// 当前table相关信息
var table = {
    config: {},
    // 当前实例配置
    options: {},
    // 设置实例配置
    set: function(id) {
    	if($.common.getLength(table.config) > 1) {
    		var tableId = $.common.isEmpty(id) ? $(event.currentTarget).parents(".bootstrap-table").find(".table").attr("id") : id;
            if ($.common.isNotEmpty(tableId)) {
                table.options = table.get(tableId);
            }
    	}
    },
    // 获取实例配置
    get: function(id) {
        return table.config[id];
    },
    // 记住选择实例组
    rememberSelecteds: {},
    // 记住选择ID组
    rememberSelectedIds: {}
};

(function ($) {
    $.extend({
    	_tree: {},
    	bttTable: {},
    	// 表格封装处理
    	table: {
            // 初始化表格参数
            init: function(options) {
            	var defaults = {
            		id: "bootstrap-table",
            		type: 0, // 0 代表bootstrapTable 1代表bootstrapTreeTable
        		    height: undefined,
        		    sidePagination: "server",
        		    sortName: "",
        		    sortOrder: "asc",
        		    pagination: true,
        		    pageSize: 10,
        		    pageList: [10, 25, 50],
        		    toolbar: "toolbar",
        		    striped: false,
        		    escape: false,
        		    firstLoad: true,
        		    showFooter: false,
        		    search: false,
                    showSearch: true,
                    showPageGo: false,
                    showRefresh: true,
                    showColumns: true,
                    showToggle: true,
                    showExport: false,
                    clickToSelect: false,
                    singleSelect: false,
                    mobileResponsive: true,
                    rememberSelected: false,
        		    fixedColumns: false,
        		    fixedNumber: 0,
        		    rightFixedColumns: false,
        		    rightFixedNumber: 0,
        		    queryParams: $.table.queryParams,
        		    rowStyle: {},
        		};
            	var options = $.extend(defaults, options);
            	table.options = options;
            	table.config[options.id] = options;
                $.table.initEvent();
                $('#' + options.id).bootstrapTable({
                	id: options.id,
                    url: options.url,                                   // 请求后台的URL（*）
                    contentType: "application/x-www-form-urlencoded",   // 编码类型
                    method: 'post',                                     // 请求方式（*）
                    cache: false,                                       // 是否使用缓存
                    height: options.height,                             // 表格的高度
                    striped: options.striped,                           // 是否显示行间隔色
                    sortable: true,                                     // 是否启用排序
                    sortStable: true,                                   // 设置为 true 将获得稳定的排序
                    sortName: options.sortName,                         // 排序列名称
                    sortOrder: options.sortOrder,                       // 排序方式  asc 或者 desc
                    pagination: options.pagination,                     // 是否显示分页（*）
                    pageNumber: 1,                                      // 初始化加载第一页，默认第一页
                    pageSize: options.pageSize,                         // 每页的记录行数（*） 
                    pageList: options.pageList,                         // 可供选择的每页的行数（*）
                    firstLoad: options.firstLoad,                       // 是否首次请求加载数据，对于数据较大可以配置false
                    escape: options.escape,                             // 转义HTML字符串
                    showFooter: options.showFooter,                     // 是否显示表尾
                    iconSize: 'outline',                                // 图标大小：undefined默认的按钮尺寸 xs超小按钮sm小按钮lg大按钮
                    toolbar: '#' + options.toolbar,                     // 指定工作栏
                    sidePagination: options.sidePagination,             // server启用服务端分页client客户端分页
                    search: options.search,                             // 是否显示搜索框功能
                    searchText: options.searchText,                     // 搜索框初始显示的内容，默认为空
                    showSearch: options.showSearch,                     // 是否显示检索信息
                    showPageGo: options.showPageGo,               		// 是否显示跳转页
                    showRefresh: options.showRefresh,                   // 是否显示刷新按钮
                    showColumns: options.showColumns,                   // 是否显示隐藏某列下拉框
                    showToggle: options.showToggle,                     // 是否显示详细视图和列表视图的切换按钮
                    showExport: options.showExport,                     // 是否支持导出文件
                    uniqueId: options.uniqueId,                         // 唯 一的标识符
                    clickToSelect: options.clickToSelect,				// 是否启用点击选中行
                    singleSelect: options.singleSelect,                 // 是否单选checkbox
                    mobileResponsive: options.mobileResponsive,         // 是否支持移动端适配
                    detailView: options.detailView,                     // 是否启用显示细节视图
                    onClickRow: options.onClickRow,                     // 点击某行触发的事件
                    onDblClickRow: options.onDblClickRow,               // 双击某行触发的事件
                    onClickCell: options.onClickCell,                   // 单击某格触发的事件
                    onDblClickCell: options.onDblClickCell,             // 双击某格触发的事件
                    onEditableSave: options.onEditableSave,             // 行内编辑保存的事件
                    onExpandRow: options.onExpandRow,                   // 点击详细视图的事件
                    rememberSelected: options.rememberSelected,         // 启用翻页记住前面的选择
                    fixedColumns: options.fixedColumns,                 // 是否启用冻结列（左侧）
                    fixedNumber: options.fixedNumber,                   // 列冻结的个数（左侧）
                    rightFixedColumns: options.rightFixedColumns,       // 是否启用冻结列（右侧）
                    rightFixedNumber: options.rightFixedNumber,         // 列冻结的个数（右侧）
                    onReorderRow: options.onReorderRow,                 // 当拖拽结束后处理函数
                    queryParams: options.queryParams,                   // 传递参数（*）
                    rowStyle: options.rowStyle,                         // 通过自定义函数设置行样式
                    columns: options.columns,                           // 显示列信息（*）
                    responseHandler: $.table.responseHandler,           // 在加载服务器发送来的数据之前处理函数
                    onLoadSuccess: $.table.onLoadSuccess,               // 当所有数据被加载时触发处理函数
                    exportOptions: options.exportOptions,               // 前端导出忽略列索引
                    detailFormatter: options.detailFormatter,           // 在行下面展示其他数据列表
                });
            },
            // 获取实例ID，如存在多个返回#id1,#id2 delimeter分隔符
            getOptionsIds: function(separator) {
            	var _separator = $.common.isEmpty(separator) ? "," : separator;
            	var optionsIds = "";  
            	$.each(table.config, function(key, value){
            		optionsIds += "#" + key + _separator;
            	});
            	return optionsIds.substring(0, optionsIds.length - 1);
            },
            // 查询条件
            queryParams: function(params) {
            	var curParams = {
            			// 传递参数查询参数
                        pageSize:       params.limit,
                        pageNum:        params.offset / params.limit + 1,
                        searchValue:    params.search,
                        orderByColumn:  params.sort,
                        isAsc:          params.order
            		};
            	var currentId = $.common.isEmpty(table.options.formId) ? $('form').attr('id') : table.options.formId;
            	return $.extend(curParams, $.common.formToJSON(currentId)); 
            },
            // 请求获取数据后处理回调函数
            responseHandler: function(res) {
            	if (typeof table.options.responseHandler == "function") {
            		table.options.responseHandler(res);
                }
                if (res.code == 0) {
                    if ($.common.isNotEmpty(table.options.sidePagination) && table.options.sidePagination == 'client') {
                    	return res.rows;
                    } else {
                    	if ($.common.isNotEmpty(table.options.rememberSelected) && table.options.rememberSelected) {
                    		var column = $.common.isEmpty(table.options.uniqueId) ? table.options.columns[1].field : table.options.uniqueId;
                    		$.each(res.rows, function(i, row) {
                    			row.state = $.inArray(row[column], table.rememberSelectedIds[table.options.id]) !== -1;
                            })
                    	}
                        return { rows: res.rows, total: res.total };
                    }
                } else {
                    $.modal.alertWarning(res.msg);
                    return { rows: [], total: 0 };
                }
            },
            // 初始化事件
            initEvent: function() {
            	// 实例ID信息
            	var optionsIds = $.table.getOptionsIds();
            	// 监听事件处理
            	$(optionsIds).on(TABLE_EVENTS, function () {
            		table.set($(this).attr("id"));
            	});
            	// 选中、取消、全部选中、全部取消（事件）
            	$(optionsIds).on("check.bs.table check-all.bs.table uncheck.bs.table uncheck-all.bs.table", function (e, rows) {
            		// 复选框分页保留保存选中数组
            		var rowIds = $.table.affectedRowIds(rows);
            		if ($.common.isNotEmpty(table.options.rememberSelected) && table.options.rememberSelected) {
            			func = $.inArray(e.type, ['check', 'check-all']) > -1 ? 'union' : 'difference';
            			var selectedIds = table.rememberSelectedIds[table.options.id];
            			if($.common.isNotEmpty(selectedIds)) {
            				table.rememberSelectedIds[table.options.id] = _[func](selectedIds, rowIds);
            			} else {
            				table.rememberSelectedIds[table.options.id] = _[func]([], rowIds);
            			}
            			var selectedRows = table.rememberSelecteds[table.options.id];
            			if($.common.isNotEmpty(selectedRows)) {
            				table.rememberSelecteds[table.options.id] = _[func](selectedRows, rows);
            			} else {
            				table.rememberSelecteds[table.options.id] = _[func]([], rows);
            			}
            		}
            	});
            	// 加载成功、选中、取消、全部选中、全部取消（事件）
            	$(optionsIds).on("check.bs.table uncheck.bs.table check-all.bs.table uncheck-all.bs.table load-success.bs.table", function () {
            		var toolbar = table.options.toolbar;
            		var uniqueId = table.options.uniqueId;
            		// 工具栏按钮控制
            		var rows = $.common.isEmpty(uniqueId) ? $.table.selectFirstColumns() : $.table.selectColumns(uniqueId);
            		// 非多个禁用
            		$('#' + toolbar + ' .multiple').toggleClass('disabled', !rows.length);
            		// 非单个禁用
            		$('#' + toolbar + ' .single').toggleClass('disabled', rows.length!=1);
            	});
            	// 图片预览事件
            	$(optionsIds).off("click").on("click", '.img-circle', function() {
    			    var src = $(this).attr('src');
    			    var target = $(this).data('target');
    			    var height = $(this).data('height');
    			    var width = $(this).data('width');
    			    if($.common.equals("self", target)) {
    			    	layer.open({
        			        title: false,
        			        type: 1,
        			        closeBtn: true,
        			        shadeClose: true,
        			        area: ['auto', 'auto'],
        			        content: "<img src='" + src + "' height='" + height + "' width='" + width + "'/>"
        			    });
    			    } else if ($.common.equals("blank", target)) {
    			        window.open(src);
    			    }
    			});
            	// 单击tooltip事件
            	$(optionsIds).on("click", '.tooltip-show', function() {
            		var target = $(this).data('target');
            		var input = $(this).prev();
            		if ($.common.equals("copy", target)) {
            		    input.select();
            		    document.execCommand("copy");
            		} else if ($.common.equals("open", target)) {
            			parent.layer.alert(input.val(), {
                	        title: "信息内容",
                	        shadeClose: true,
                	        btn: ['确认'],
                	        btnclass: ['btn btn-primary'],
                	    });
            		}
            	});
            },
            // 当所有数据被加载时触发
            onLoadSuccess: function(data) {
            	if (typeof table.options.onLoadSuccess == "function") {
            		table.options.onLoadSuccess(data);
            	}
            	// 浮动提示框特效
            	$("[data-toggle='tooltip']").tooltip();
            },
            // 表格销毁
            destroy: function (tableId) {
            	var currentId = $.common.isEmpty(tableId) ? table.options.id : tableId;
            	$("#" + currentId).bootstrapTable('destroy');
	        },
            // 序列号生成
            serialNumber: function (index, tableId) {
            	var currentId = $.common.isEmpty(tableId) ? table.options.id : tableId;
				var tableParams = $("#" + currentId).bootstrapTable('getOptions');
				var pageSize = tableParams.pageSize;
				var pageNumber = tableParams.pageNumber;
				return pageSize * (pageNumber - 1) + index + 1;
			},
			// 列超出指定长度浮动提示 target（copy单击复制文本 open弹窗打开文本）
			tooltip: function (value, length, target) {
				var _length = $.common.isEmpty(length) ? 20 : length;
				var _text = "";
				var _value = $.common.nullToStr(value);
				var _target = $.common.isEmpty(target) ? 'copy' : target;
				if (_value.length > _length) {
					_text = _value.substr(0, _length) + "...";
					_value = _value.replace(/\'/g,"&apos;");
					_value = _value.replace(/\"/g,"&quot;");
					var actions = [];
					actions.push($.common.sprintf('<input id="tooltip-show" style="opacity: 0;position: absolute;z-index:-1" type="text" value="%s"/>', _value));
                	actions.push($.common.sprintf('<a href="###" class="tooltip-show" data-toggle="tooltip" data-target="%s" title="%s">%s</a>', _target, _value, _text));
					return actions.join('');
				} else {
					_text = _value;
					return _text;
				}
			},
			// 下拉按钮切换
			dropdownToggle: function (value) {
				var actions = [];
				actions.push('<div class="btn-group">');
				actions.push('<button type="button" class="btn btn-xs dropdown-toggle" data-toggle="dropdown" aria-expanded="false">');
				actions.push('<i class="fa fa-cog"></i>&nbsp;<span class="fa fa-chevron-down"></span></button>');
				actions.push('<ul class="dropdown-menu">');
				actions.push(value.replace(/<a/g,"<li><a").replace(/<\/a>/g,"</a></li>"));
				actions.push('</ul>');
				actions.push('</div>');
				return actions.join('');
			},
			// 图片预览
			imageView: function (value, height, width, target) {
				if ($.common.isEmpty(width)) {
                	width = 'auto';
                }
                if ($.common.isEmpty(height)) {
                	height = 'auto';
                }
				// blank or self
				var _target = $.common.isEmpty(target) ? 'self' : target;
				if ($.common.isNotEmpty(value)) {
					return $.common.sprintf("<img class='img-circle img-xs' data-height='%s' data-width='%s' data-target='%s' src='%s'/>", height, width, _target, value);
				} else {
					return $.common.nullToStr(value);
				}
			},
            // 搜索-默认第一个form
            search: function(formId, tableId, data) {
            	table.set(tableId);
            	var currentId = $.common.isEmpty(formId) ? $('form').attr('id') : formId;
            	var params = $.common.isEmpty(tableId) ? $("#" + table.options.id).bootstrapTable('getOptions') : $("#" + tableId).bootstrapTable('getOptions');
            	params.queryParams = function(params) {
                    var search = $.common.formToJSON(currentId);
                    if($.common.isNotEmpty(data)){
	                    $.each(data, function(key) {
	                        search[key] = data[key];
	                    });
                    }
                    search.pageSize = params.limit;
                    search.pageNum = params.offset / params.limit + 1;
                    search.searchValue = params.search;
                    search.orderByColumn = params.sort;
                    search.isAsc = params.order;
    		        return search;
    		    }
    		    if($.common.isNotEmpty(tableId)){
    				$("#" + tableId).bootstrapTable('refresh', params);
    			} else{
    				$("#" + table.options.id).bootstrapTable('refresh', params);
    			}
    		    data = {};
    		},
    		// 导出数据
    		exportExcel: function(formId) {
    			table.set();
    			$.modal.confirm("确定导出所有" + table.options.modalName + "吗？", function() {
	    			var currentId = $.common.isEmpty(formId) ? $('form').attr('id') : formId;
	    			var params = $("#" + table.options.id).bootstrapTable('getOptions');
	    			var dataParam = $("#" + currentId).serializeArray();
	    			dataParam.push({ "name": "orderByColumn", "value": params.sortName });
	    			dataParam.push({ "name": "isAsc", "value": params.sortOrder });
	    			$.modal.loading("正在导出数据，请稍后...");
	    			$.post(table.options.exportUrl, dataParam, function(result) {
	    				if (result.code == web_status.SUCCESS) {
	    			        window.location.href = ctx + "common/download?fileName=" + encodeURI(result.msg) + "&delete=" + true;
	    				} else if (result.code == web_status.WARNING) {
	                        $.modal.alertWarning(result.msg)
	                    } else {
	    					$.modal.alertError(result.msg);
	    				}
	    				$.modal.closeLoading();
	    			});
    			});
    		},
			// 导出数据
			exportWord: function(formId) {
				table.set();
				$.modal.confirm("确定导出所有" + table.options.modalName + "吗？", function() {
					var currentId = $.common.isEmpty(formId) ? $('form').attr('id') : formId;
					var params = $("#" + table.options.id).bootstrapTable('getOptions');
					var dataParam = $("#" + currentId).serializeArray();
					dataParam.push({ "name": "orderByColumn", "value": params.sortName });
					dataParam.push({ "name": "isAsc", "value": params.sortOrder });
					$.modal.loading("正在导出数据，请稍后...");
					$.post(table.options.exportWordUrl, dataParam, function(result) {
						if (result.code == web_status.SUCCESS) {
							window.location.href = ctx + "common/download?fileName=" + encodeURI(result.msg) + "&delete=" + true;
						} else if (result.code == web_status.WARNING) {
							$.modal.alertWarning(result.msg)
						} else {
							$.modal.alertError(result.msg);
						}
						$.modal.closeLoading();
					});
				});
			},
			// 导出数据
			exportExcel1: function(url) {
				$.modal.confirm("确定导出所有吗？", function() {
					$.modal.loading("正在导出数据，请稍后...");
					$.post(ctx + url, function(result) {
						if (result.code == web_status.SUCCESS) {
							window.location.href = ctx + "common/download?fileName=" + encodeURI(result.msg) + "&delete=" + true;
						} else if (result.code == web_status.WARNING) {
							$.modal.alertWarning(result.msg)
						} else {
							$.modal.alertError(result.msg);
						}
						$.modal.closeLoading();
					});
				});
			},
			// 导出数据
			exportWord1: function(url) {
				$.modal.confirm("确定导出所有吗？", function() {
					$.modal.loading("正在导出数据，请稍后...");
					$.post(ctx + url, function(result) {
						if (result.code == web_status.SUCCESS) {
							window.location.href = ctx + "common/download?fileName=" + encodeURI(result.msg) + "&delete=" + true;
						} else if (result.code == web_status.WARNING) {
							$.modal.alertWarning(result.msg)
						} else {
							$.modal.alertError(result.msg);
						}
						$.modal.closeLoading();
					});
				});
			},

    		// 下载模板
    		importTemplate: function() {
    			table.set();
    			$.get(table.options.importTemplateUrl, function(result) {
    				if (result.code == web_status.SUCCESS) {
    			        window.location.href = ctx + "common/download?fileName=" + encodeURI(result.msg) + "&delete=" + true;
    				} else if (result.code == web_status.WARNING) {
                        $.modal.alertWarning(result.msg)
                    } else {
    					$.modal.alertError(result.msg);
    				}
    			});
            },
            // 导入数据
            importExcel: function(formId) {
            	table.set();
            	var currentId = $.common.isEmpty(formId) ? 'importTpl' : formId;
            	layer.open({
            		type: 1,
            		area: ['400px', '230px'],
            		fix: false,
            		//不固定
            		maxmin: true,
            		shade: 0.3,
            		title: '导入' + table.options.modalName + '数据',
            		content: $('#' + currentId).html(),
            		btn: ['<i class="fa fa-check"></i> 导入', '<i class="fa fa-remove"></i> 取消'],
            		// 弹层外区域关闭
            		shadeClose: true,
            		btn1: function(index, layero){
            			var file = layero.find('#file').val();
            			if (file == '' || (!$.common.endWith(file, '.xls') && !$.common.endWith(file, '.xlsx'))){
            				$.modal.msgWarning("请选择后缀为 “xls”或“xlsx”的文件。");
            				return false;
            			}
            			var index = layer.load(2, {shade: false});
            			$.modal.disable();
            			var formData = new FormData();
            			formData.append("file", layero.find('#file')[0].files[0]);
            			formData.append("updateSupport", $("input[name='updateSupport']").is(':checked'));
            			$.ajax({
            				url: table.options.importUrl,
            				data: formData,
            				cache: false,
            				contentType: false,
            				processData: false,
            				type: 'POST',
            				success: function (result) {
            					if (result.code == web_status.SUCCESS) {
            						$.modal.closeAll();
            						$.modal.alertSuccess(result.msg);
            						$.table.refresh();
            					} else if (result.code == web_status.WARNING) {
            						layer.close(index);
            						$.modal.enable();
        	                        $.modal.alertWarning(result.msg)
        	                    } else {
            						layer.close(index);
            						$.modal.enable();
            						$.modal.alertError(result.msg);
            					}
            				}
            			});
            		}
            	});
            },
            // 刷新表格
            refresh: function(tableId) {
            	var currentId = $.common.isEmpty(tableId) ? table.options.id : tableId;
            	$("#" + currentId).bootstrapTable('refresh', {
                    silent: true
                });
            },
            // 查询表格指定列值
            selectColumns: function(column) {
            	var rows = $.map($("#" + table.options.id).bootstrapTable('getSelections'), function (row) {
        	        return row[column];
        	    });
            	if ($.common.isNotEmpty(table.options.rememberSelected) && table.options.rememberSelected) {
            		var selectedRows = table.rememberSelecteds[table.options.id];
            		if($.common.isNotEmpty(selectedRows)) {
	            		rows = $.map(table.rememberSelecteds[table.options.id], function (row) {
	                        return row[column];
	                    });
            		}
            	}
            	return $.common.uniqueFn(rows);
            },
            // 获取当前页选中或者取消的行ID
            affectedRowIds: function(rows) {
            	var column = $.common.isEmpty(table.options.uniqueId) ? table.options.columns[1].field : table.options.uniqueId;
            	var rowIds;
            	if ($.isArray(rows)) {
            	    rowIds = $.map(rows, function(row) {
            	        return row[column];
            	    });
            	} else {
            	    rowIds = [rows[column]];
            	}
            	return rowIds;
            },
            // 查询表格首列值
            selectFirstColumns: function() {
            	var rows = $.map($("#" + table.options.id).bootstrapTable('getSelections'), function (row) {
        	        return row[table.options.columns[1].field];
        	    });
            	if ($.common.isNotEmpty(table.options.rememberSelected) && table.options.rememberSelected) {
            		var selectedRows = table.rememberSelecteds[table.options.id];
            		if($.common.isNotEmpty(selectedRows)) {
            			rows = $.map(selectedRows, function (row) {
                            return row[table.options.columns[1].field];
                        });
            		}
            	}
            	return $.common.uniqueFn(rows);
            },
            // 回显数据字典
            selectDictLabel: function(datas, value) {
            	var actions = [];
                $.each(datas, function(index, dict) {
                    if (dict.dictValue == ('' + value)) {
                    	var listClass = $.common.equals("default", dict.listClass) || $.common.isEmpty(dict.listClass) ? "" : "badge badge-" + dict.listClass;
                    	actions.push($.common.sprintf("<span class='%s'>%s</span>", listClass, dict.dictLabel));
                        return false;
                    }
                });
                return actions.join('');
            },
            // 显示表格指定列
            showColumn: function(column, tableId) {
            	var currentId = $.common.isEmpty(tableId) ? table.options.id : tableId;
            	$("#" + currentId).bootstrapTable('showColumn', column);
            },
            // 隐藏表格指定列
            hideColumn: function(column, tableId) {
            	var currentId = $.common.isEmpty(tableId) ? table.options.id : tableId;
            	$("#" + currentId).bootstrapTable('hideColumn', column);
            }
        },
        // 表格树封装处理
        treeTable: {
            // 初始化表格
            init: function(options) {
            	var defaults = {
            		id: "bootstrap-tree-table",
                    type: 1, // 0 代表bootstrapTable 1代表bootstrapTreeTable
        		    height: 0,
        		    rootIdValue: null,
        		    ajaxParams: {},
        		    toolbar: "toolbar",
        		    striped: false,
        		    expandColumn: 1,
        		    showSearch: true,
        		    showRefresh: true,
        			showColumns: true,
        			expandAll: true,
        			expandFirst: true
        		};
            	var options = $.extend(defaults, options);
            	table.options = options;
            	table.config[options.id] = options;
                $.bttTable = $('#' + options.id).bootstrapTreeTable({
                	code: options.code,                                 // 用于设置父子关系
        		    parentCode: options.parentCode,                     // 用于设置父子关系
        	    	type: 'post',                                       // 请求方式（*）
        	        url: options.url,                                   // 请求后台的URL（*）
        	        data: options.data,                                 // 无url时用于渲染的数据
        	        ajaxParams: options.ajaxParams,                     // 请求数据的ajax的data属性
        	        rootIdValue: options.rootIdValue,                   // 设置指定根节点id值
        	        height: options.height,                             // 表格树的高度
        			expandColumn: options.expandColumn,                 // 在哪一列上面显示展开按钮
        			striped: options.striped,                           // 是否显示行间隔色
        			bordered: false,                                    // 是否显示边框
        			toolbar: '#' + options.toolbar,                     // 指定工作栏
        			showSearch: options.showSearch,                     // 是否显示检索信息
        			showRefresh: options.showRefresh,                   // 是否显示刷新按钮
        			showColumns: options.showColumns,                   // 是否显示隐藏某列下拉框
        			expandAll: options.expandAll,                       // 是否全部展开
        			expandFirst: options.expandFirst,                   // 是否默认第一级展开--expandAll为false时生效
        	        columns: options.columns,                           // 显示列信息（*）
        	        responseHandler: $.treeTable.responseHandler        // 当所有数据被加载时触发处理函数
        	    });
            },
            // 条件查询
            search: function(formId) {
            	var currentId = $.common.isEmpty(formId) ? $('form').attr('id') : formId;
            	var params = $.common.formToJSON(currentId);
                $.bttTable.bootstrapTreeTable('refresh', params);
            },
            // 刷新
            refresh: function() {
            	$.bttTable.bootstrapTreeTable('refresh');
            },
            // 查询表格树指定列值
            selectColumns: function(column) {
            	var rows = $.map($.bttTable.bootstrapTreeTable('getSelections'), function (row) {
        	        return row[column];
        	    });
            	return $.common.uniqueFn(rows);
            },
            // 请求获取数据后处理回调函数，校验异常状态提醒
            responseHandler: function(data) {
            	if (data.code != undefined && data.code != 0) {
            		$.modal.alertWarning(data.msg);
            		return [];
                } else {
                    return data;
                }
            },
        },
        // 表单封装处理
    	form: {
    		// 表单重置
    		reset: function(formId, tableId) {
    			table.set(tableId);
            	var currentId = $.common.isEmpty(formId) ? $('form').attr('id') : formId;
            	$("#" + currentId)[0].reset();
            	if (table.options.type == table_type.bootstrapTable) {
            	    if($.common.isEmpty(tableId)){
            	    	$("#" + table.options.id).bootstrapTable('refresh');
                	} else{
                	    $("#" + tableId).bootstrapTable('refresh');
                	}
            	} else if (table.options.type == table_type.bootstrapTreeTable) {
            		if($.common.isEmpty(tableId)){
            	    	$("#" + table.options.id).bootstrapTreeTable('refresh', []);
                	} else{
                	    $("#" + tableId).bootstrapTreeTable('refresh', []);
                	}
            	}
            },
            // 获取选中复选框项
            selectCheckeds: function(name) {
            	var checkeds = "";
        	    $('input:checkbox[name="' + name + '"]:checked').each(function(i) {
        	        if (0 == i) {
        	        	checkeds = $(this).val();
        	        } else {
        	        	checkeds += ("," + $(this).val());
        	        }
        	    });
        	    return checkeds;
            },
            // 获取选中下拉框项
            selectSelects: function(name) {
            	var selects = "";
        	    $('#' + name + ' option:selected').each(function (i) {
        	        if (0 == i) {
        	        	selects = $(this).val();
        	        } else {
        	        	selects += ("," + $(this).val());
        	        }
        	    });
        	    return selects;
            }
        },
        // 弹出层封装处理
    	modal: {
    		// 显示图标
    		icon: function(type) {
            	var icon = "";
        	    if (type == modal_status.WARNING) {
        	        icon = 0;
        	    } else if (type == modal_status.SUCCESS) {
        	        icon = 1;
        	    } else if (type == modal_status.FAIL) {
        	        icon = 2;
        	    } else {
        	        icon = 3;
        	    }
        	    return icon;
            },
    		// 消息提示
            msg: function(content, type) {
            	if (type != undefined) {
                    layer.msg(content, { icon: $.modal.icon(type), time: 1000, shift: 5 });
                } else {
                    layer.msg(content);
                }
            },
            // 错误消息
            msgError: function(content) {
            	$.modal.msg(content, modal_status.FAIL);
            },
            // 成功消息
            msgSuccess: function(content) {
            	$.modal.msg(content, modal_status.SUCCESS);
            },
            // 警告消息
            msgWarning: function(content) {
            	$.modal.msg(content, modal_status.WARNING);
            },
    		// 弹出提示
            alert: function(content, type) {
        	    layer.alert(content, {
        	        icon: $.modal.icon(type),
        	        title: "系统提示",
        	        btn: ['确认'],
        	        btnclass: ['btn btn-primary'],
        	    });
            },
            // 消息提示并刷新父窗体
            msgReload: function(msg, type) {
            	layer.msg(msg, {
            	    icon: $.modal.icon(type),
            	    time: 500,
            	    shade: [0.1, '#8F8F8F']
            	},
            	function() {
            	    $.modal.reload();
            	});
            },
            // 错误提示
            alertError: function(content) {
            	$.modal.alert(content, modal_status.FAIL);
            },
            // 成功提示
            alertSuccess: function(content) {
            	$.modal.alert(content, modal_status.SUCCESS);
            },
            // 警告提示
            alertWarning: function(content) {
            	$.modal.alert(content, modal_status.WARNING);
            },
            // 关闭窗体
            close: function () {
            	var index = parent.layer.getFrameIndex(window.name);
                parent.layer.close(index);
            },
            // 关闭全部窗体
            closeAll: function () {
                layer.closeAll();
            },
            // 确认窗体
            confirm: function (content, callBack) {
            	layer.confirm(content, {
        	        icon: 3,
        	        title: "系统提示",
        	        btn: ['确认', '取消']
        	    }, function (index) {
        	    	layer.close(index);
        	        callBack(true);
        	    });
            },
            // 弹出层指定宽度
            open: function (title, url, width, height, callback) {
            	//如果是移动端，就使用自适应大小弹窗
            	if (navigator.userAgent.match(/(iPhone|iPod|Android|ios)/i)) {
            	    width = 'auto';
            	    height = 'auto';
            	}
            	if ($.common.isEmpty(title)) {
                    title = false;
                }
                if ($.common.isEmpty(url)) {
                    url = "/404.html";
                }
                if ($.common.isEmpty(width)) {
                	width = 800;
                }
                if ($.common.isEmpty(height)) {
                	height = ($(window).height() - 50);
                }
                if ($.common.isEmpty(callback)) {
                    callback = function(index, layero) {
                        var iframeWin = layero.find('iframe')[0];
                        iframeWin.contentWindow.submitHandler(index, layero);
                    }
                }
            	layer.open({
            		type: 2,
            		area: [width + 'px', height + 'px'],
            		fix: false,
            		//不固定
            		maxmin: true,
            		shade: 0.3,
            		title: title,
            		content: url,
            		btn: ['确定', '关闭'],
            	    // 弹层外区域关闭
            		shadeClose: true,
            		yes: callback,
            	    cancel: function(index) {
            	        return true;
            	    }
            	});
            },
            // 弹出层指定参数选项
            openOptions: function (options) {
            	var _url = $.common.isEmpty(options.url) ? "/404.html" : options.url; 
            	var _title = $.common.isEmpty(options.title) ? "系统窗口" : options.title; 
                var _width = $.common.isEmpty(options.width) ? "800" : options.width; 
                var _height = $.common.isEmpty(options.height) ? ($(window).height() - 50) : options.height;
                var _btn = ['<i class="fa fa-check"></i> 确认', '<i class="fa fa-close"></i> 关闭'];
                if ($.common.isEmpty(options.yes)) {
                	options.yes = function(index, layero) {
                    	options.callBack(index, layero);
                    }
                }
                layer.open({
                    type: 2,
            		maxmin: true,
                    shade: 0.3,
                    title: _title,
                    fix: false,
                    area: [_width + 'px', _height + 'px'],
                    content: _url,
                    shadeClose: $.common.isEmpty(options.shadeClose) ? true : options.shadeClose,
                    skin: options.skin,
                    btn: $.common.isEmpty(options.btn) ? _btn : options.btn,
                    yes: options.yes,
                    cancel: function () {
                        return true;
                    }
                });
            },
            // 弹出层全屏
            openFull: function (title, url, width, height) {
            	//如果是移动端，就使用自适应大小弹窗
            	if (navigator.userAgent.match(/(iPhone|iPod|Android|ios)/i)) {
            	    width = 'auto';
            	    height = 'auto';
            	}
            	if ($.common.isEmpty(title)) {
                    title = false;
                }
                if ($.common.isEmpty(url)) {
                    url = "/404.html";
                }
                if ($.common.isEmpty(width)) {
                	width = 800;
                }
                if ($.common.isEmpty(height)) {
                	height = ($(window).height() - 50);
                }
                var index = layer.open({
            		type: 2,
            		area: [width + 'px', height + 'px'],
            		fix: false,
            		//不固定
            		maxmin: true,
            		shade: 0.3,
            		title: title,
            		content: url,
            		btn: ['确定', '关闭'],
            		// 弹层外区域关闭
            		shadeClose: true,
            		yes: function(index, layero) {
            	        var iframeWin = layero.find('iframe')[0];
            	        iframeWin.contentWindow.submitHandler(index, layero);
            	    },
            	    cancel: function(index) {
            	        return true;
            	    }
            	});
                layer.full(index);
            },
            // 选卡页方式打开
            openTab: function (title, url) {
            	createMenuItem(url, title);
            },
            // 选卡页同一页签打开
            parentTab: function (title, url) {
            	var dataId = window.frameElement.getAttribute('data-id');
            	createMenuItem(url, title);
            	closeItem(dataId);
            },
            // 关闭选项卡
            closeTab: function (dataId) {
            	closeItem(dataId);
            },
            // 禁用按钮
            disable: function() {
            	var doc = window.top == window.parent ? window.document : window.parent.document;
	        	$("a[class*=layui-layer-btn]", doc).addClass("layer-disabled");
            },
            // 启用按钮
            enable: function() {
            	var doc = window.top == window.parent ? window.document : window.parent.document;
            	$("a[class*=layui-layer-btn]", doc).removeClass("layer-disabled");
            },
            // 打开遮罩层
            loading: function (message) {
            	$.blockUI({ message: '<div class="loaderbox"><div class="loading-activity"></div> ' + message + '</div>' });
            },
            // 关闭遮罩层
            closeLoading: function () {
            	setTimeout(function(){
            		$.unblockUI();
            	}, 50);
            },
            // 重新加载
            reload: function () {
            	parent.location.reload();
            }
        },
        // 操作封装处理
        operate: {
        	// 提交数据
        	submit: function(url, type, dataType, data, callback) {
            	var config = {
        	        url: url,
        	        type: type,
        	        dataType: dataType,
        	        data: data,
        	        beforeSend: function () {
        	        	$.modal.loading("正在处理中，请稍后...");
        	        },
        	        success: function(result) {
        	        	if (typeof callback == "function") {
        	        	    callback(result);
        	        	}
        	        	$.operate.ajaxSuccess(result);
        	        }
        	    };
        	    $.ajax(config)
            },
            // post请求传输
            post: function(url, data, callback) {
            	$.operate.submit(url, "post", "json", data, callback);
            },
            // get请求传输
            get: function(url, callback) {
            	$.operate.submit(url, "get", "json", "", callback);
            },
            // 详细信息
            detail: function(id, width, height) {
            	table.set();
            	var _url = $.operate.detailUrl(id);
            	var _width = $.common.isEmpty(width) ? "800" : width; 
                var _height = $.common.isEmpty(height) ? ($(window).height() - 50) : height;
            	//如果是移动端，就使用自适应大小弹窗
            	if (navigator.userAgent.match(/(iPhone|iPod|Android|ios)/i)) {
            	    _width = 'auto';
            	    _height = 'auto';
            	}
            	var options = {
       				title: table.options.modalName + "详细",
       				width: _width,
       				height: _height,
       				url: _url,
       				skin: 'layui-layer-gray', 
       				btn: ['关闭'],
       				yes: function (index, layero) {
       	                layer.close(index);
                    }
       			};
            	$.modal.openOptions(options);
            },
            // 详细访问地址
            detailUrl: function(id) {
            	var url = "/404.html";
            	if ($.common.isNotEmpty(id)) {
            	    url = table.options.detailUrl.replace("{id}", id);
            	} else {
            	    var id = $.common.isEmpty(table.options.uniqueId) ? $.table.selectFirstColumns() : $.table.selectColumns(table.options.uniqueId);
            	    if (id.length == 0) {
            			$.modal.alertWarning("请至少选择一条记录");
            			return;
            		}
            	    url = table.options.detailUrl.replace("{id}", id);
            	}
                return url;
            },
            // 删除信息
            remove: function(id) {
            	table.set();
            	$.modal.confirm("确定删除该条" + table.options.modalName + "信息吗？", function() {
                    var url = $.common.isEmpty(id) ? table.options.removeUrl : table.options.removeUrl.replace("{id}", id);
                    if(table.options.type == table_type.bootstrapTreeTable) {
                    	$.operate.get(url);
                    } else {
	            	    var data = { "ids": id };
	            	    $.operate.submit(url, "post", "json", data);
	                }
            	});
            	
            },
            // 批量删除信息
            removeAll: function() {
            	table.set();
        		var rows = $.common.isEmpty(table.options.uniqueId) ? $.table.selectFirstColumns() : $.table.selectColumns(table.options.uniqueId);
        		if (rows.length == 0) {
        			$.modal.alertWarning("请至少选择一条记录");
        			return;
        		}
        		$.modal.confirm("确认要删除选中的" + rows.length + "条数据吗?", function() {
        			var url = table.options.removeUrl;
        			var data = { "ids": rows.join() };
        			$.operate.submit(url, "post", "json", data);
        		});
            },
            // 清空信息
            clean: function() {
            	table.set();
            	$.modal.confirm("确定清空所有" + table.options.modalName + "吗？", function() {
	            	var url = table.options.cleanUrl;
	            	$.operate.submit(url, "post", "json", "");
            	});
            },
            // 添加信息
            add: function(id) {
            	table.set();
            	$.modal.open("添加" + table.options.modalName, $.operate.addUrl(id));
            },
            // 添加信息，以tab页展现
            addTab: function (id) {
            	table.set();
                $.modal.openTab("添加" + table.options.modalName, $.operate.addUrl(id));
            },
            // 添加信息 全屏
            addFull: function(id) {
            	table.set();
            	var url = $.common.isEmpty(id) ? table.options.createUrl : table.options.createUrl.replace("{id}", id);
                $.modal.openFull("添加" + table.options.modalName, url);
            },
            // 添加访问地址
            addUrl: function(id) {
            	var url = $.common.isEmpty(id) ? table.options.createUrl.replace("{id}", "") : table.options.createUrl.replace("{id}", id);
                return url;
            },
            // 修改信息
            edit: function(id) {
            	table.set();
            	if($.common.isEmpty(id) && table.options.type == table_type.bootstrapTreeTable) {
            		var row = $("#" + table.options.id).bootstrapTreeTable('getSelections')[0];
                	if ($.common.isEmpty(row)) {
            			$.modal.alertWarning("请至少选择一条记录");
            			return;
            		}
                    var url = table.options.updateUrl.replace("{id}", row[table.options.uniqueId]);
                    $.modal.open("修改" + table.options.modalName, url);
            	} else {
            	    $.modal.open("修改" + table.options.modalName, $.operate.editUrl(id));
            	}
            },
            // 修改信息，以tab页展现
            editTab: function(id) {
            	table.set();
            	$.modal.openTab("修改" + table.options.modalName, $.operate.editUrl(id));
            },
            // 修改信息 全屏
            editFull: function(id) {
            	table.set();
            	var url = "/404.html";
            	if ($.common.isNotEmpty(id)) {
            	    url = table.options.updateUrl.replace("{id}", id);
            	} else {
            	    var row = $.common.isEmpty(table.options.uniqueId) ? $.table.selectFirstColumns() : $.table.selectColumns(table.options.uniqueId);
            	    url = table.options.updateUrl.replace("{id}", row);
            	}
            	$.modal.openFull("修改" + table.options.modalName, url);
            },
            // 修改访问地址
            editUrl: function(id) {
            	var url = "/404.html";
            	if ($.common.isNotEmpty(id)) {
            	    url = table.options.updateUrl.replace("{id}", id);
            	} else {
            	    var id = $.common.isEmpty(table.options.uniqueId) ? $.table.selectFirstColumns() : $.table.selectColumns(table.options.uniqueId);
            	    if (id.length == 0) {
            			$.modal.alertWarning("请至少选择一条记录");
            			return;
            		}
            	    url = table.options.updateUrl.replace("{id}", id);
            	}
                return url;
            },
            // 保存信息 刷新表格
            save: function(url, data, callback) {
            	var config = {
        	        url: url,
        	        type: "post",
        	        dataType: "json",
        	        data: data,
        	        beforeSend: function () {
        	        	$.modal.loading("正在处理中，请稍后...");
        	        	$.modal.disable();
        	        },
        	        success: function(result) {
        	        	if (typeof callback == "function") {
        	        	    callback(result);
        	        	}
        	        	$.operate.successCallback(result);
        	        }
        	    };
        	    $.ajax(config)
            },
            // 保存信息 弹出提示框
            saveModal: function(url, data, callback) {
            	var config = {
        	        url: url,
        	        type: "post",
        	        dataType: "json",
        	        data: data,
        	        beforeSend: function () {
        	        	$.modal.loading("正在处理中，请稍后...");
        	        },
        	        success: function(result) {
        	        	if (typeof callback == "function") {
        	        	    callback(result);
        	        	}
        	        	if (result.code == web_status.SUCCESS) {
	                        $.modal.alertSuccess(result.msg)
	                    } else if (result.code == web_status.WARNING) {
	                        $.modal.alertWarning(result.msg)
	                    } else {
	                    	$.modal.alertError(result.msg);
	                    }
        	        	$.modal.closeLoading();
        	        }
        	    };
        	    $.ajax(config)
            },
            // 保存选项卡信息
            saveTab: function(url, data, callback) {
            	var config = {
        	        url: url,
        	        type: "post",
        	        dataType: "json",
        	        data: data,
        	        beforeSend: function () {
        	        	$.modal.loading("正在处理中，请稍后...");
        	        },
        	        success: function(result) {
        	        	if (typeof callback == "function") {
        	        	    callback(result);
        	        	}
        	        	$.operate.successTabCallback(result);
        	        }
        	    };
        	    $.ajax(config)
            },
            // 保存结果弹出msg刷新table表格
            ajaxSuccess: function (result) {
            	if (result.code == web_status.SUCCESS && table.options.type == table_type.bootstrapTable) {
                	$.modal.msgSuccess(result.msg);
            		$.table.refresh();
                } else if (result.code == web_status.SUCCESS && table.options.type == table_type.bootstrapTreeTable) {
                	$.modal.msgSuccess(result.msg);
                	$.treeTable.refresh();
                } else if (result.code == web_status.WARNING) {
                    $.modal.alertWarning(result.msg)
                }  else {
                	$.modal.alertError(result.msg);
                }
            	$.modal.closeLoading();
            },
            // 成功结果提示msg（父窗体全局更新）
            saveSuccess: function (result) {
            	if (result.code == web_status.SUCCESS) {
            		$.modal.msgReload("保存成功,正在刷新数据请稍后……", modal_status.SUCCESS);
                } else if (result.code == web_status.WARNING) {
                    $.modal.alertWarning(result.msg)
                }  else {
                	$.modal.alertError(result.msg);
                }
            	$.modal.closeLoading();
            },
            // 成功回调执行事件（父窗体静默更新）
            successCallback: function(result) {
                if (result.code == web_status.SUCCESS) {
                	var parent = window.parent;
                    if (parent.table.options.type == table_type.bootstrapTable) {
                        $.modal.close();
                        parent.$.modal.msgSuccess(result.msg);
                        parent.$.table.refresh();
                    } else if (parent.table.options.type == table_type.bootstrapTreeTable) {
                        $.modal.close();
                        parent.$.modal.msgSuccess(result.msg);
                        parent.$.treeTable.refresh();
                    } else {
                        $.modal.msgReload("保存成功,正在刷新数据请稍后……", modal_status.SUCCESS);
                    }
                } else if (result.code == web_status.WARNING) {
                    $.modal.alertWarning(result.msg)
                }  else {
                    $.modal.alertError(result.msg);
                }
                $.modal.closeLoading();
                $.modal.enable();
            },
            // 选项卡成功回调执行事件（父窗体静默更新）
            successTabCallback: function(result) {
                if (result.code == web_status.SUCCESS) {
                	var topWindow = $(window.parent.document);
    	            var currentId = $('.page-tabs-content', topWindow).find('.active').attr('data-panel');
    	            var $contentWindow = $('.RuoYi_iframe[data-id="' + currentId + '"]', topWindow)[0].contentWindow;
    	            $.modal.close();
    	            $contentWindow.$.modal.msgSuccess(result.msg);
    	            $contentWindow.$(".layui-layer-padding").removeAttr("style");
    	            if ($contentWindow.table.options.type == table_type.bootstrapTable) {
    	        		$contentWindow.$.table.refresh();
    	        	} else if ($contentWindow.table.options.type == table_type.bootstrapTreeTable) {
    	        		$contentWindow.$.treeTable.refresh();
                    }
    	            $.modal.closeTab();
                } else if (result.code == web_status.WARNING) {
                    $.modal.alertWarning(result.msg)
                } else {
                    $.modal.alertError(result.msg);
                }
                $.modal.closeLoading();
            }
        },
        // 校验封装处理
        validate: {
        	// 判断返回标识是否唯一 false 不存在 true 存在
        	unique: function (value) {
            	if (value == "0") {
                    return true;
                }
                return false;
            },
            // 表单验证
            form: function (formId) {
            	var currentId = $.common.isEmpty(formId) ? $('form').attr('id') : formId;
                return $("#" + currentId).validate().form();
            },
            // 重置表单验证（清除提示信息）
            reset: function (formId) {
            	var currentId = $.common.isEmpty(formId) ? $('form').attr('id') : formId;
                return $("#" + currentId).validate().resetForm();
            }
        },
        // 树插件封装处理
        tree: {
        	_option: {},
        	_lastValue: {},
        	// 初始化树结构
        	init: function(options) {
        		var defaults = {
            		id: "tree",                    // 属性ID
            		expandLevel: 0,                // 展开等级节点
            		view: {
    			        selectedMulti: false,      // 设置是否允许同时选中多个节点
    			        nameIsHTML: true           // 设置 name 属性是否支持 HTML 脚本
    			    },
            		check: {
    				    enable: false,             // 置 zTree 的节点上是否显示 checkbox / radio
    				    nocheckInherit: true,      // 设置子节点是否自动继承
    				},
    				data: {
    			        key: {
    			            title: "title"         // 节点数据保存节点提示信息的属性名称
    			        },
    			        simpleData: {
    			            enable: true           // true / false 分别表示 使用 / 不使用 简单数据模式
    			        }
    			    },
        		};
            	var options = $.extend(defaults, options);
        		$.tree._option = options;
        		// 树结构初始化加载
        		var setting = {
    				callback: {
    			        onClick: options.onClick,                      // 用于捕获节点被点击的事件回调函数
    			        onCheck: options.onCheck,                      // 用于捕获 checkbox / radio 被勾选 或 取消勾选的事件回调函数
    			        onDblClick: options.onDblClick                 // 用于捕获鼠标双击之后的事件回调函数
    			    },
    				check: options.check,
    			    view: options.view,
    			    data: options.data
    			};
        	    $.get(options.url, function(data) {
        			var treeId = $("#treeId").val();
        			tree = $.fn.zTree.init($("#" + options.id), setting, data);
        			$._tree = tree;
        			var nodes = tree.getNodesByParam("level", options.expandLevel - 1);
        			for (var i = 0; i < nodes.length; i++) {
        				tree.expandNode(nodes[i], true, false, false);
        			}
        			var node = tree.getNodesByParam("id", treeId, null)[0];
        			$.tree.selectByIdName(treeId, node);
        		});
        	},
        	// 搜索节点
        	searchNode: function() {
        		// 取得输入的关键字的值
        		var value = $.common.trim($("#keyword").val());
        		if ($.tree._lastValue == value) {
        		    return;
        		}
        		// 保存最后一次搜索名称
        		$.tree._lastValue = value;
        		var nodes = $._tree.getNodes();
        		// 如果要查空字串，就退出不查了。
        		if (value == "") {
        		    $.tree.showAllNode(nodes);
        		    return;
        		}
        		$.tree.hideAllNode(nodes);
        		// 根据搜索值模糊匹配
        		$.tree.updateNodes($._tree.getNodesByParamFuzzy("name", value));
        	},
        	// 根据Id和Name选中指定节点
        	selectByIdName: function(treeId, node) {
        		if ($.common.isNotEmpty(treeId) && treeId == node.id) {
        			$._tree.selectNode(node, true);
        		}
        	},
        	// 显示所有节点
        	showAllNode: function(nodes) {
        		nodes = $._tree.transformToArray(nodes);
        		for (var i = nodes.length - 1; i >= 0; i--) {
        		    if (nodes[i].getParentNode() != null) {
        		    	$._tree.expandNode(nodes[i], true, false, false, false);
        		    } else {
        		    	$._tree.expandNode(nodes[i], true, true, false, false);
        		    }
        		    $._tree.showNode(nodes[i]);
        		    $.tree.showAllNode(nodes[i].children);
        		}
        	},
        	// 隐藏所有节点
        	hideAllNode: function(nodes) {
        	    var tree = $.fn.zTree.getZTreeObj("tree");
        	    var nodes = $._tree.transformToArray(nodes);
        	    for (var i = nodes.length - 1; i >= 0; i--) {
        	    	$._tree.hideNode(nodes[i]);
        	    }
        	},
        	// 显示所有父节点
        	showParent: function(treeNode) {
        		var parentNode;
        		while ((parentNode = treeNode.getParentNode()) != null) {
        			$._tree.showNode(parentNode);
        			$._tree.expandNode(parentNode, true, false, false);
        		    treeNode = parentNode;
        		}
        	},
        	// 显示所有孩子节点
        	showChildren: function(treeNode) {
        		if (treeNode.isParent) {
        		    for (var idx in treeNode.children) {
        		        var node = treeNode.children[idx];
        		        $._tree.showNode(node);
        		        $.tree.showChildren(node);
        		    }
        		}
        	},
        	// 更新节点状态
        	updateNodes: function(nodeList) {
        		$._tree.showNodes(nodeList);
        		for (var i = 0, l = nodeList.length; i < l; i++) {
        		    var treeNode = nodeList[i];
        		    $.tree.showChildren(treeNode);
        		    $.tree.showParent(treeNode)
        		}
        	},
        	// 获取当前被勾选集合
        	getCheckedNodes: function(column) {
        		var _column = $.common.isEmpty(column) ? "id" : column;
        		var nodes = $._tree.getCheckedNodes(true);
        		return $.map(nodes, function (row) {
        	        return row[_column];
        	    }).join();
        	},
        	// 不允许根父节点选择
        	notAllowParents: function(_tree) {
    		    var nodes = _tree.getSelectedNodes();
    		    if(nodes.length == 0){
                    $.modal.msgError("请选择节点后提交");
                    return false;
				}
    		    for (var i = 0; i < nodes.length; i++) {
    		        if (nodes[i].level == 0) {
    		            $.modal.msgError("不能选择根节点（" + nodes[i].name + "）");
    		            return false;
    		        }
    		        if (nodes[i].isParent) {
    		            $.modal.msgError("不能选择父节点（" + nodes[i].name + "）");
    		            return false;
    		        }
    		    }
        		return true;
        	},
        	// 不允许最后层级节点选择
        	notAllowLastLevel: function(_tree) {
    		    var nodes = _tree.getSelectedNodes();
    		    for (var i = 0; i < nodes.length; i++) {
                    if (!nodes[i].isParent) {
    		    		$.modal.msgError("不能选择最后层级节点（" + nodes[i].name + "）");
    		            return false;
    		        }
    		    }
        		return true;
        	},
        	// 隐藏/显示搜索栏
        	toggleSearch: function() {
        		$('#search').slideToggle(200);
        		$('#btnShow').toggle();
        		$('#btnHide').toggle();
        		$('#keyword').focus();
        	},
        	// 折叠
        	collapse: function() {
        		$._tree.expandAll(false);
        	},
        	// 展开
        	expand: function() {
        		$._tree.expandAll(true);
        	}
        },
        // 通用方法封装处理
    	common: {
    		// 判断字符串是否为空
            isEmpty: function (value) {
                if (value == null || this.trim(value) == "") {
                    return true;
                }
                return false;
            },
            // 判断一个字符串是否为非空串
            isNotEmpty: function (value) {
            	return !$.common.isEmpty(value);
            },
            // 空对象转字符串
            nullToStr: function(value) {
                if ($.common.isEmpty(value)) {
                    return "-";
                }
                return value;
            },
            // 是否显示数据 为空默认为显示
            visible: function (value) {
                if ($.common.isEmpty(value) || value == true) {
                    return true;
                }
                return false;
            },
            // 空格截取
            trim: function (value) {
                if (value == null) {
                    return "";
                }
                return value.toString().replace(/(^\s*)|(\s*$)|\r|\n/g, "");
            },
            // 比较两个字符串（大小写敏感）
            equals: function (str, that) {
            	return str == that;
            },
            // 比较两个字符串（大小写不敏感）
            equalsIgnoreCase: function (str, that) {
            	return String(str).toUpperCase() === String(that).toUpperCase();
            },
            // 将字符串按指定字符分割
            split: function (str, sep, maxLen) {
            	if ($.common.isEmpty(str)) {
            	    return null;
            	}
            	var value = String(str).split(sep);
            	return maxLen ? value.slice(0, maxLen - 1) : value;
            },
            // 字符串格式化(%s )
            sprintf: function (str) {
                var args = arguments, flag = true, i = 1;
                str = str.replace(/%s/g, function () {
                    var arg = args[i++];
                    if (typeof arg === 'undefined') {
                        flag = false;
                        return '';
                    }
                    return arg;
                });
                return flag ? str : '';
            },
            // 指定随机数返回
            random: function (min, max) {
                return Math.floor((Math.random() * max) + min);
            },
            // 判断字符串是否是以start开头
            startWith: function(value, start) {
                var reg = new RegExp("^" + start);
                return reg.test(value)
            },
            // 判断字符串是否是以end结尾
            endWith: function(value, end) {
                var reg = new RegExp(end + "$");
                return reg.test(value)
            },
            // 数组去重
            uniqueFn: function(array) {
                var result = [];
                var hashObj = {};
                for (var i = 0; i < array.length; i++) {
                    if (!hashObj[array[i]]) {
                        hashObj[array[i]] = true;
                        result.push(array[i]);
                    }
                }
                return result;
            },
            // 数组中的所有元素放入一个字符串
            join: function(array, separator) {
            	if ($.common.isEmpty(array)) {
            	    return null;
            	}
                return array.join(separator);
            },
            // 获取form下所有的字段并转换为json对象
            formToJSON: function(formId) {
            	 var json = {};
                 $.each($("#" + formId).serializeArray(), function(i, field) {
                 	 if(json[field.name]) {
                         json[field.name] += ("," + field.value);
					 } else {
                         json[field.name] = field.value;
                     }
                 });
            	return json;
            },
            // 获取obj对象长度
            getLength: function(obj) {
                var count = 0;　　
                for (var i in obj) {
                    if (obj.hasOwnProperty(i)) {
                        count++;
                    }　　
                }
                return count;
            }
        }
    });
})(jQuery);

/** 表格类型 */
table_type = {
    bootstrapTable: 0,
    bootstrapTreeTable: 1
};

/** 消息状态码 */
web_status = {
    SUCCESS: 0,
    FAIL: 500,
    WARNING: 301
};

/** 弹窗状态码 */
modal_status = {
    SUCCESS: "success",
    FAIL: "error",
    WARNING: "warning"
};

var provinceList = [
	{name:'北京', cityList:[
			{name:'市辖区', areaList:['东城区','西城区','崇文区','宣武区','朝阳区','丰台区','石景山区','海淀区','门头沟区','房山区','通州区','顺义区','昌平区','大兴区','怀柔区','平谷区']},
			{name:'县', areaList:['密云县','延庆县']}
		]},
	{name:'上海', cityList:[
			{name:'市辖区', areaList:['黄浦区','卢湾区','徐汇区','长宁区','静安区','普陀区','闸北区','虹口区','杨浦区','闵行区','宝山区','嘉定区','浦东新区','金山区','松江区','青浦区','南汇区','奉贤区']},
			{name:'县', areaList:['崇明县']}
		]},
	{name:'天津', cityList:[
			{name:'市辖区', areaList:['和平区','河东区','河西区','南开区','河北区','红桥区','塘沽区','汉沽区','大港区','东丽区','西青区','津南区','北辰区','武清区','宝坻区']},
			{name:'县', areaList:['宁河县','静海县','蓟　县']}
		]},
	{name:'重庆', cityList:[
			{name:'市辖区', areaList:['万州区','涪陵区','渝中区','大渡口区','江北区','沙坪坝区','九龙坡区','南岸区','北碚区','万盛区','双桥区','渝北区','巴南区','黔江区','长寿区']},
			{name:'县', areaList:['綦江县','潼南县','铜梁县','大足县','荣昌县','璧山县','梁平县','城口县','丰都县','垫江县','武隆县','忠　县','开　县','云阳县','奉节县','巫山县','巫溪县','石柱土家族自治县','秀山土家族苗族自治县','酉阳土家族苗族自治县','彭水苗族土家族自治县']},
			{name:'市', areaList:['江津市','合川市','永川市','南川市']}
		]},
	{name:'四川', cityList:[
			{name:'成都市', areaList:['市辖区','锦江区','青羊区','金牛区','武侯区','成华区','龙泉驿区','青白江区','新都区','温江县','金堂县','双流县','郫　县','大邑县','蒲江县','新津县','都江堰市','彭州市','邛崃市','崇州市']},
			{name:'自贡市', areaList:['市辖区','自流井区','贡井区','大安区','沿滩区','荣　县','富顺县']},
			{name:'攀枝花市', areaList:['市辖区','东　区','西　区','仁和区','米易县','盐边县']},
			{name:'泸州市', areaList:['市辖区','江阳区','纳溪区','龙马潭区','泸　县','合江县','叙永县','古蔺县']},
			{name:'德阳市', areaList:['市辖区','旌阳区','中江县','罗江县','广汉市','什邡市','绵竹市']},
			{name:'绵阳市', areaList:['市辖区','涪城区','游仙区','三台县','盐亭县','安　县','梓潼县','北川羌族自治县','平武县','江油市']},
			{name:'广元市', areaList:['市辖区','市中区','元坝区','朝天区','旺苍县','青川县','剑阁县','苍溪县']},
			{name:'遂宁市', areaList:['市辖区','船山区','安居区','蓬溪县','射洪县','大英县']},
			{name:'内江市', areaList:['市辖区','市中区','东兴区','威远县','资中县','隆昌县']},
			{name:'乐山市', areaList:['市辖区','市中区','沙湾区','五通桥区','金口河区','犍为县','井研县','夹江县','沐川县','峨边彝族自治县','马边彝族自治县','峨眉山市']},
			{name:'南充市', areaList:['市辖区','顺庆区','高坪区','嘉陵区','南部县','营山县','蓬安县','仪陇县','西充县','阆中市']},
			{name:'眉山市', areaList:['市辖区','东坡区','仁寿县','彭山县','洪雅县','丹棱县','青神县']},
			{name:'宜宾市', areaList:['市辖区','翠屏区','宜宾县','南溪县','江安县','长宁县','高　县','珙　县','筠连县','兴文县','屏山县']},
			{name:'广安市', areaList:['市辖区','广安区','岳池县','武胜县','邻水县','华莹市']},
			{name:'达州市', areaList:['市辖区','通川区','达　县','宣汉县','开江县','大竹县','渠　县','万源市']},
			{name:'雅安市', areaList:['市辖区','雨城区','名山县','荥经县','汉源县','石棉县','天全县','芦山县','宝兴县']},
			{name:'巴中市', areaList:['市辖区','巴州区','通江县','南江县','平昌县']},
			{name:'资阳市', areaList:['市辖区','雁江区','安岳县','乐至县','简阳市']},
			{name:'阿坝藏族羌族自治州', areaList:['汶川县','理　县','茂　县','松潘县','九寨沟县','金川县','小金县','黑水县','马尔康县','壤塘县','阿坝县','若尔盖县','红原县']},
			{name:'甘孜藏族自治州', areaList:['康定县','泸定县','丹巴县','九龙县','雅江县','道孚县','炉霍县','甘孜县','新龙县','德格县','白玉县','石渠县','色达县','理塘县','巴塘县','乡城县','稻城县','得荣县']},
			{name:'凉山彝族自治州', areaList:['西昌市','木里藏族自治县','盐源县','德昌县','会理县','会东县','宁南县','普格县','布拖县','金阳县','昭觉县','喜德县','冕宁县','越西县','甘洛县','美姑县','雷波县']}
		]},
	{name:'贵州', cityList:[
			{name:'贵阳市', areaList:['市辖区','南明区','云岩区','花溪区','乌当区','白云区','小河区','开阳县','息烽县','修文县','清镇市']},
			{name:'六盘水市', areaList:['钟山区','六枝特区','水城县','盘　县']},
			{name:'遵义市', areaList:['市辖区','红花岗区','汇川区','遵义县','桐梓县','绥阳县','正安县','道真仡佬族苗族自治县','务川仡佬族苗族自治县','凤冈县','湄潭县','余庆县','习水县','赤水市','仁怀市']},
			{name:'安顺市', areaList:['市辖区','西秀区','平坝县','普定县','镇宁布依族苗族自治县','关岭布依族苗族自治县','紫云苗族布依族自治县']},
			{name:'铜仁地区', areaList:['铜仁市','江口县','玉屏侗族自治县','石阡县','思南县','印江土家族苗族自治县','德江县','沿河土家族自治县','松桃苗族自治县','万山特区']},
			{name:'黔西南布依族苗族自治州', areaList:['兴义市','兴仁县','普安县','晴隆县','贞丰县','望谟县','册亨县','安龙县']},
			{name:'毕节地区', areaList:['毕节市','大方县','黔西县','金沙县','织金县','纳雍县','威宁彝族回族苗族自治县','赫章县']},
			{name:'黔东南苗族侗族自治州', areaList:['凯里市','黄平县','施秉县','三穗县','镇远县','岑巩县','天柱县','锦屏县','剑河县','台江县','黎平县','榕江县','从江县','雷山县','麻江县','丹寨县']},
			{name:'黔南布依族苗族自治州', areaList:['都匀市','福泉市','荔波县','贵定县','瓮安县','独山县','平塘县','罗甸县','长顺县','龙里县','惠水县','三都水族自治县']}
		]},
	{name:'云南', cityList:[
			{name:'昆明市', areaList:['市辖区','五华区','盘龙区','官渡区','西山区','东川区','呈贡县','晋宁县','富民县','宜良县','石林彝族自治县','嵩明县','禄劝彝族苗族自治县','寻甸回族彝族自治县','安宁市']},
			{name:'曲靖市', areaList:['市辖区','麒麟区','马龙县','陆良县','师宗县','罗平县','富源县','会泽县','沾益县','宣威市']},
			{name:'玉溪市', areaList:['市辖区','红塔区','江川县','澄江县','通海县','华宁县','易门县','峨山彝族自治县','新平彝族傣族自治县','元江哈尼族彝族傣族自治县']},
			{name:'保山市', areaList:['市辖区','隆阳区','施甸县','腾冲县','龙陵县','昌宁县']},
			{name:'昭通市', areaList:['市辖区','昭阳区','鲁甸县','巧家县','盐津县','大关县','永善县','绥江县','镇雄县','彝良县','威信县','水富县']},
			{name:'丽江市', areaList:['市辖区','古城区','玉龙纳西族自治县','永胜县','华坪县','宁蒗彝族自治县']},
			{name:'思茅市', areaList:['市辖区','翠云区','普洱哈尼族彝族自治县','墨江哈尼族自治县','景东彝族自治县','景谷傣族彝族自治县','镇沅彝族哈尼族拉祜族自治县','江城哈尼族彝族自治县','孟连傣族拉祜族佤族自治县','澜沧拉祜族自治县','西盟佤族自治县']},
			{name:'临沧市', areaList:['市辖区','临翔区','凤庆县','云　县','永德县','镇康县','双江拉祜族佤族布朗族傣族自治县','耿马傣族佤族自治县','沧源佤族自治县']},
			{name:'楚雄彝族自治州', areaList:['楚雄市','双柏县','牟定县','南华县','姚安县','大姚县','永仁县','元谋县','武定县','禄丰县']},
			{name:'红河哈尼族彝族自治州', areaList:['个旧市','开远市','蒙自县','屏边苗族自治县','建水县','石屏县','弥勒县','泸西县','元阳县','红河县','金平苗族瑶族傣族自治县','绿春县','河口瑶族自治县']},
			{name:'文山壮族苗族自治州', areaList:['文山县','砚山县','西畴县','麻栗坡县','马关县','丘北县','广南县','富宁县']},
			{name:'西双版纳傣族自治州', areaList:['景洪市','勐海县','勐腊县']},
			{name:'大理白族自治州', areaList:['大理市','漾濞彝族自治县','祥云县','宾川县','弥渡县','南涧彝族自治县','巍山彝族回族自治县','永平县','云龙县','洱源县','剑川县','鹤庆县']},
			{name:'德宏傣族景颇族自治州', areaList:['瑞丽市','潞西市','梁河县','盈江县','陇川县']},
			{name:'怒江傈僳族自治州', areaList:['泸水县','福贡县','贡山独龙族怒族自治县','兰坪白族普米族自治县']},
			{name:'迪庆藏族自治州', areaList:['香格里拉县','德钦县','维西傈僳族自治县']}
		]},
	{name:'西藏', cityList:[
			{name:'拉萨市', areaList:['市辖区','城关区','林周县','当雄县','尼木县','曲水县','堆龙德庆县','达孜县','墨竹工卡县']},
			{name:'昌都地区', areaList:['昌都县','江达县','贡觉县','类乌齐县','丁青县','察雅县','八宿县','左贡县','芒康县','洛隆县','边坝县']},
			{name:'山南地区', areaList:['乃东县','扎囊县','贡嘎县','桑日县','琼结县','曲松县','措美县','洛扎县','加查县','隆子县','错那县','浪卡子县']},
			{name:'日喀则地区', areaList:['日喀则市','南木林县','江孜县','定日县','萨迦县','拉孜县','昂仁县','谢通门县','白朗县','仁布县','康马县','定结县','仲巴县','亚东县','吉隆县','聂拉木县','萨嘎县','岗巴县']},
			{name:'那曲地区', areaList:['那曲县','嘉黎县','比如县','聂荣县','安多县','申扎县','索　县','班戈县','巴青县','尼玛县']},
			{name:'阿里地区', areaList:['普兰县','札达县','噶尔县','日土县','革吉县','改则县','措勤县']},
			{name:'林芝地区', areaList:['林芝县','工布江达县','米林县','墨脱县','波密县','察隅县','朗　县']}
		]},
	{name:'河南', cityList:[
			{name:'郑州市', areaList:['市辖区','中原区','二七区','管城回族区','金水区','上街区','邙山区','中牟县','巩义市','荥阳市','新密市','新郑市','登封市']},
			{name:'开封市', areaList:['市辖区','龙亭区','顺河回族区','鼓楼区','南关区','郊　区','杞　县','通许县','尉氏县','开封县','兰考县']},
			{name:'洛阳市', areaList:['市辖区','老城区','西工区','廛河回族区','涧西区','吉利区','洛龙区','孟津县','新安县','栾川县','嵩　县','汝阳县','宜阳县','洛宁县','伊川县','偃师市']},
			{name:'平顶山市', areaList:['市辖区','新华区','卫东区','石龙区','湛河区','宝丰县','叶　县','鲁山县','郏　县','舞钢市','汝州市']},
			{name:'安阳市', areaList:['市辖区','文峰区','北关区','殷都区','龙安区','安阳县','汤阴县','滑　县','内黄县','林州市']},
			{name:'鹤壁市', areaList:['市辖区','鹤山区','山城区','淇滨区','浚　县','淇　县']},
			{name:'新乡市', areaList:['市辖区','红旗区','卫滨区','凤泉区','牧野区','新乡县','获嘉县','原阳县','延津县','封丘县','长垣县','卫辉市','辉县市']},
			{name:'焦作市', areaList:['市辖区','解放区','中站区','马村区','山阳区','修武县','博爱县','武陟县','温　县','济源市','沁阳市','孟州市']},
			{name:'濮阳市', areaList:['市辖区','华龙区','清丰县','南乐县','范　县','台前县','濮阳县']},
			{name:'许昌市', areaList:['市辖区','魏都区','许昌县','鄢陵县','襄城县','禹州市','长葛市']},
			{name:'漯河市', areaList:['市辖区','源汇区','郾城区','召陵区','舞阳县','临颍县']},
			{name:'三门峡市', areaList:['市辖区','湖滨区','渑池县','陕　县','卢氏县','义马市','灵宝市']},
			{name:'南阳市', areaList:['市辖区','宛城区','卧龙区','南召县','方城县','西峡县','镇平县','内乡县','淅川县','社旗县','唐河县','新野县','桐柏县','邓州市']},
			{name:'商丘市', areaList:['市辖区','梁园区','睢阳区','民权县','睢　县','宁陵县','柘城县','虞城县','夏邑县','永城市']},
			{name:'信阳市', areaList:['市辖区','师河区','平桥区','罗山县','光山县','新　县','商城县','固始县','潢川县','淮滨县','息　县']},
			{name:'周口市', areaList:['市辖区','川汇区','扶沟县','西华县','商水县','沈丘县','郸城县','淮阳县','太康县','鹿邑县','项城市']},
			{name:'驻马店市', areaList:['市辖区','驿城区','西平县','上蔡县','平舆县','正阳县','确山县','泌阳县','汝南县','遂平县','新蔡县']}
		]},
	{name:'湖北', cityList:[
			{name:'武汉市', areaList:['市辖区','江岸区','江汉区','乔口区','汉阳区','武昌区','青山区','洪山区','东西湖区','汉南区','蔡甸区','江夏区','黄陂区','新洲区']},
			{name:'黄石市', areaList:['市辖区','黄石港区','西塞山区','下陆区','铁山区','阳新县','大冶市']},
			{name:'十堰市', areaList:['市辖区','茅箭区','张湾区','郧　县','郧西县','竹山县','竹溪县','房　县','丹江口市']},
			{name:'宜昌市', areaList:['市辖区','西陵区','伍家岗区','点军区','猇亭区','夷陵区','远安县','兴山县','秭归县','长阳土家族自治县','五峰土家族自治县','宜都市','当阳市','枝江市']},
			{name:'襄樊市', areaList:['市辖区','襄城区','樊城区','襄阳区','南漳县','谷城县','保康县','老河口市','枣阳市','宜城市']},
			{name:'鄂州市', areaList:['市辖区','梁子湖区','华容区','鄂城区']},
			{name:'荆门市', areaList:['市辖区','东宝区','掇刀区','京山县','沙洋县','钟祥市']},
			{name:'孝感市', areaList:['市辖区','孝南区','孝昌县','大悟县','云梦县','应城市','安陆市','汉川市']},
			{name:'荆州市', areaList:['市辖区','沙市区','荆州区','公安县','监利县','江陵县','石首市','洪湖市','松滋市']},
			{name:'黄冈市', areaList:['市辖区','黄州区','团风县','红安县','罗田县','英山县','浠水县','蕲春县','黄梅县','麻城市','武穴市']},
			{name:'咸宁市', areaList:['市辖区','咸安区','嘉鱼县','通城县','崇阳县','通山县','赤壁市']},
			{name:'随州市', areaList:['市辖区','曾都区','广水市']},
			{name:'恩施土家族苗族自治州', areaList:['恩施市','利川市','建始县','巴东县','宣恩县','咸丰县','来凤县','鹤峰县']},
			{name:'省直辖行政单位', areaList:['仙桃市','潜江市','天门市','神农架林区']}
		]},
	{name:'湖南', cityList:[
			{name:'长沙市', areaList:['市辖区','芙蓉区','天心区','岳麓区','开福区','雨花区','长沙县','望城县','宁乡县','浏阳市']},
			{name:'株洲市', areaList:['市辖区','荷塘区','芦淞区','石峰区','天元区','株洲县','攸　县','茶陵县','炎陵县','醴陵市']},
			{name:'湘潭市', areaList:['市辖区','雨湖区','岳塘区','湘潭县','湘乡市','韶山市']},
			{name:'衡阳市', areaList:['市辖区','珠晖区','雁峰区','石鼓区','蒸湘区','南岳区','衡阳县','衡南县','衡山县','衡东县','祁东县','耒阳市','常宁市']},
			{name:'邵阳市', areaList:['市辖区','双清区','大祥区','北塔区','邵东县','新邵县','邵阳县','隆回县','洞口县','绥宁县','新宁县','城步苗族自治县','武冈市']},
			{name:'岳阳市', areaList:['市辖区','岳阳楼区','云溪区','君山区','岳阳县','华容县','湘阴县','平江县','汨罗市','临湘市']},
			{name:'常德市', areaList:['市辖区','武陵区','鼎城区','安乡县','汉寿县','澧　县','临澧县','桃源县','石门县','津市市']},
			{name:'张家界市', areaList:['市辖区','永定区','武陵源区','慈利县','桑植县']},
			{name:'益阳市', areaList:['市辖区','资阳区','赫山区','南　县','桃江县','安化县','沅江市']},
			{name:'郴州市', areaList:['市辖区','北湖区','苏仙区','桂阳县','宜章县','永兴县','嘉禾县','临武县','汝城县','桂东县','安仁县','资兴市']},
			{name:'永州市', areaList:['市辖区','芝山区','冷水滩区','祁阳县','东安县','双牌县','道　县','江永县','宁远县','蓝山县','新田县','江华瑶族自治县']},
			{name:'怀化市', areaList:['市辖区','鹤城区','中方县','沅陵县','辰溪县','溆浦县','会同县','麻阳苗族自治县','新晃侗族自治县','芷江侗族自治县','靖州苗族侗族自治县','通道侗族自治县','洪江市']},
			{name:'娄底市', areaList:['市辖区','娄星区','双峰县','新化县','冷水江市','涟源市']},
			{name:'湘西土家族苗族自治州', areaList:['吉首市','泸溪县','凤凰县','花垣县','保靖县','古丈县','永顺县','龙山县']}
		]},
	{name:'广东', cityList:[
			{name:'广州市', areaList:['市辖区','东山区','荔湾区','越秀区','海珠区','天河区','芳村区','白云区','黄埔区','番禺区','花都区','增城市','从化市']},
			{name:'韶关市', areaList:['市辖区','武江区','浈江区','曲江区','始兴县','仁化县','翁源县','乳源瑶族自治县','新丰县','乐昌市','南雄市']},
			{name:'深圳市', areaList:['市辖区','罗湖区','福田区','南山区','宝安区','龙岗区','盐田区']},
			{name:'珠海市', areaList:['市辖区','香洲区','斗门区','金湾区']},
			{name:'汕头市', areaList:['市辖区','龙湖区','金平区','濠江区','潮阳区','潮南区','澄海区','南澳县']},
			{name:'佛山市', areaList:['市辖区','禅城区','南海区','顺德区','三水区','高明区']},
			{name:'江门市', areaList:['市辖区','蓬江区','江海区','新会区','台山市','开平市','鹤山市','恩平市']},
			{name:'湛江市', areaList:['市辖区','赤坎区','霞山区','坡头区','麻章区','遂溪县','徐闻县','廉江市','雷州市','吴川市']},
			{name:'茂名市', areaList:['市辖区','茂南区','茂港区','电白县','高州市','化州市','信宜市']},
			{name:'肇庆市', areaList:['市辖区','端州区','鼎湖区','广宁县','怀集县','封开县','德庆县','高要市','四会市']},
			{name:'惠州市', areaList:['市辖区','惠城区','惠阳区','博罗县','惠东县','龙门县']},
			{name:'梅州市', areaList:['市辖区','梅江区','梅　县','大埔县','丰顺县','五华县','平远县','蕉岭县','兴宁市']},
			{name:'汕尾市', areaList:['市辖区','城　区','海丰县','陆河县','陆丰市']},
			{name:'河源市', areaList:['市辖区','源城区','紫金县','龙川县','连平县','和平县','东源县']},
			{name:'阳江市', areaList:['市辖区','江城区','阳西县','阳东县','阳春市']},
			{name:'清远市', areaList:['市辖区','清城区','佛冈县','阳山县','连山壮族瑶族自治县','连南瑶族自治县','清新县','英德市','连州市']},
			{name:'东莞市', areaList:['东莞市']},
			{name:'中山市', areaList:['中山市']},
			{name:'潮州市', areaList:['市辖区','湘桥区','潮安县','饶平县']},
			{name:'揭阳市', areaList:['市辖区','榕城区','揭东县','揭西县','惠来县','普宁市']},
			{name:'云浮市', areaList:['市辖区','云城区','新兴县','郁南县','云安县','罗定市']}
		]},
	{name:'广西', cityList:[
			{name:'南宁市', areaList:['市辖区','兴宁区','青秀区','江南区','西乡塘区','良庆区','邕宁区','武鸣县','隆安县','马山县','上林县','宾阳县','横　县']},
			{name:'柳州市', areaList:['市辖区','城中区','鱼峰区','柳南区','柳北区','柳江县','柳城县','鹿寨县','融安县','融水苗族自治县','三江侗族自治县']},
			{name:'桂林市', areaList:['市辖区','秀峰区','叠彩区','象山区','七星区','雁山区','阳朔县','临桂县','灵川县','全州县','兴安县','永福县','灌阳县','龙胜各族自治县','资源县','平乐县','荔蒲县','恭城瑶族自治县']},
			{name:'梧州市', areaList:['市辖区','万秀区','蝶山区','长洲区','苍梧县','藤　县','蒙山县','岑溪市']},
			{name:'北海市', areaList:['市辖区','海城区','银海区','铁山港区','合浦县']},
			{name:'防城港市', areaList:['市辖区','港口区','防城区','上思县','东兴市']},
			{name:'钦州市', areaList:['市辖区','钦南区','钦北区','灵山县','浦北县']},
			{name:'贵港市', areaList:['市辖区','港北区','港南区','覃塘区','平南县','桂平市']},
			{name:'玉林市', areaList:['市辖区','玉州区','容　县','陆川县','博白县','兴业县','北流市']},
			{name:'百色市', areaList:['市辖区','右江区','田阳县','田东县','平果县','德保县','靖西县','那坡县','凌云县','乐业县','田林县','西林县','隆林各族自治县']},
			{name:'贺州市', areaList:['市辖区','八步区','昭平县','钟山县','富川瑶族自治县']},
			{name:'河池市', areaList:['市辖区','金城江区','南丹县','天峨县','凤山县','东兰县','罗城仫佬族自治县','环江毛南族自治县','巴马瑶族自治县','都安瑶族自治县','大化瑶族自治县','宜州市']},
			{name:'来宾市', areaList:['市辖区','兴宾区','忻城县','象州县','武宣县','金秀瑶族自治县','合山市']},
			{name:'崇左市', areaList:['市辖区','江洲区','扶绥县','宁明县','龙州县','大新县','天等县','凭祥市']}
		]},
	{name:'陕西', cityList:[
			{name:'西安市', areaList:['市辖区','新城区','碑林区','莲湖区','灞桥区','未央区','雁塔区','阎良区','临潼区','长安区','蓝田县','周至县','户　县','高陵县']},
			{name:'铜川市', areaList:['市辖区','王益区','印台区','耀州区','宜君县']},
			{name:'宝鸡市', areaList:['市辖区','渭滨区','金台区','陈仓区','凤翔县','岐山县','扶风县','眉　县','陇　县','千阳县','麟游县','凤　县','太白县']},
			{name:'咸阳市', areaList:['市辖区','秦都区','杨凌区','渭城区','三原县','泾阳县','乾　县','礼泉县','永寿县','彬　县','长武县','旬邑县','淳化县','武功县','兴平市']},
			{name:'渭南市', areaList:['市辖区','临渭区','华　县','潼关县','大荔县','合阳县','澄城县','蒲城县','白水县','富平县','韩城市','华阴市']},
			{name:'延安市', areaList:['市辖区','宝塔区','延长县','延川县','子长县','安塞县','志丹县','吴旗县','甘泉县','富　县','洛川县','宜川县','黄龙县','黄陵县']},
			{name:'汉中市', areaList:['市辖区','汉台区','南郑县','城固县','洋　县','西乡县','勉　县','宁强县','略阳县','镇巴县','留坝县','佛坪县']},
			{name:'榆林市', areaList:['市辖区','榆阳区','神木县','府谷县','横山县','靖边县','定边县','绥德县','米脂县','佳　县','吴堡县','清涧县','子洲县']},
			{name:'安康市', areaList:['市辖区','汉滨区','汉阴县','石泉县','宁陕县','紫阳县','岚皋县','平利县','镇坪县','旬阳县','白河县']},
			{name:'商洛市', areaList:['市辖区','商州区','洛南县','丹凤县','商南县','山阳县','镇安县','柞水县']}
		]},
	{name:'甘肃', cityList:[
			{name:'兰州市', areaList:['市辖区','城关区','七里河区','西固区','安宁区','红古区','永登县','皋兰县','榆中县']},
			{name:'嘉峪关市', areaList:['市辖区']},
			{name:'金昌市', areaList:['市辖区','金川区','永昌县']},
			{name:'白银市', areaList:['市辖区','白银区','平川区','靖远县','会宁县','景泰县']},
			{name:'天水市', areaList:['市辖区','秦城区','北道区','清水县','秦安县','甘谷县','武山县','张家川回族自治县']},
			{name:'武威市', areaList:['市辖区','凉州区','民勤县','古浪县','天祝藏族自治县']},
			{name:'张掖市', areaList:['市辖区','甘州区','肃南裕固族自治县','民乐县','临泽县','高台县','山丹县']},
			{name:'平凉市', areaList:['市辖区','崆峒区','泾川县','灵台县','崇信县','华亭县','庄浪县','静宁县']},
			{name:'酒泉市', areaList:['市辖区','肃州区','金塔县','安西县','肃北蒙古族自治县','阿克塞哈萨克族自治县','玉门市','敦煌市']},
			{name:'庆阳市', areaList:['市辖区','西峰区','庆城县','环　县','华池县','合水县','正宁县','宁　县','镇原县']},
			{name:'定西市', areaList:['市辖区','安定区','通渭县','陇西县','渭源县','临洮县','漳　县','岷　县']},
			{name:'陇南市', areaList:['市辖区','武都区','成　县','文　县','宕昌县','康　县','西和县','礼　县','徽　县','两当县']},
			{name:'临夏回族自治州', areaList:['临夏市','临夏县','康乐县','永靖县','广河县','和政县','东乡族自治县','积石山保安族东乡族撒拉族自治县']},
			{name:'甘南藏族自治州', areaList:['合作市','临潭县','卓尼县','舟曲县','迭部县','玛曲县','碌曲县','夏河县']}
		]},
	{name:'青海', cityList:[
			{name:'西宁市', areaList:['市辖区','城东区','城中区','城西区','城北区','大通回族土族自治县','湟中县','湟源县']},
			{name:'海东地区', areaList:['平安县','民和回族土族自治县','乐都县','互助土族自治县','化隆回族自治县','循化撒拉族自治县']},
			{name:'海北藏族自治州', areaList:['门源回族自治县','祁连县','海晏县','刚察县']},
			{name:'黄南藏族自治州', areaList:['同仁县','尖扎县','泽库县','河南蒙古族自治县']},
			{name:'海南藏族自治州', areaList:['共和县','同德县','贵德县','兴海县','贵南县']},
			{name:'果洛藏族自治州', areaList:['玛沁县','班玛县','甘德县','达日县','久治县','玛多县']},
			{name:'玉树藏族自治州', areaList:['玉树县','杂多县','称多县','治多县','囊谦县','曲麻莱县']},
			{name:'海西蒙古族藏族自治州', areaList:['格尔木市','德令哈市','乌兰县','都兰县','天峻县']}
		]},
	{name:'宁夏', cityList:[
			{name:'银川市', areaList:['市辖区','兴庆区','西夏区','金凤区','永宁县','贺兰县','灵武市']},
			{name:'石嘴山市', areaList:['市辖区','大武口区','惠农区','平罗县']},
			{name:'吴忠市', areaList:['市辖区','利通区','盐池县','同心县','青铜峡市']},
			{name:'固原市', areaList:['市辖区','原州区','西吉县','隆德县','泾源县','彭阳县','海原县']},
			{name:'中卫市', areaList:['市辖区','沙坡头区','中宁县']}
		]},
	{name:'新疆', cityList:[
			{name:'乌鲁木齐市', areaList:['市辖区','天山区','沙依巴克区','新市区','水磨沟区','头屯河区','达坂城区','东山区','乌鲁木齐县']},
			{name:'克拉玛依市', areaList:['市辖区','独山子区','克拉玛依区','白碱滩区','乌尔禾区']},
			{name:'吐鲁番地区', areaList:['吐鲁番市','鄯善县','托克逊县']},
			{name:'哈密地区', areaList:['哈密市','巴里坤哈萨克自治县','伊吾县']},
			{name:'昌吉回族自治州', areaList:['昌吉市','阜康市','米泉市','呼图壁县','玛纳斯县','奇台县','吉木萨尔县','木垒哈萨克自治县']},
			{name:'博尔塔拉蒙古自治州', areaList:['博乐市','精河县','温泉县']},
			{name:'巴音郭楞蒙古自治州', areaList:['库尔勒市','轮台县','尉犁县','若羌县','且末县','焉耆回族自治县','和静县','和硕县','博湖县']},
			{name:'阿克苏地区', areaList:['阿克苏市','温宿县','库车县','沙雅县','新和县','拜城县','乌什县','阿瓦提县','柯坪县']},
			{name:'克孜勒苏柯尔克孜自治州', areaList:['阿图什市','阿克陶县','阿合奇县','乌恰县']},
			{name:'喀什地区', areaList:['喀什市','疏附县','疏勒县','英吉沙县','泽普县','莎车县','叶城县','麦盖提县','岳普湖县','伽师县','巴楚县','塔什库尔干塔吉克自治县']},
			{name:'和田地区', areaList:['和田市','和田县','墨玉县','皮山县','洛浦县','策勒县','于田县','民丰县']},
			{name:'伊犁哈萨克自治州', areaList:['伊宁市','奎屯市','伊宁县','察布查尔锡伯自治县','霍城县','巩留县','新源县','昭苏县','特克斯县','尼勒克县']},
			{name:'塔城地区', areaList:['塔城市','乌苏市','额敏县','沙湾县','托里县','裕民县','和布克赛尔蒙古自治县']},
			{name:'阿勒泰地区', areaList:['阿勒泰市','布尔津县','富蕴县','福海县','哈巴河县','青河县','吉木乃县']},
			{name:'省直辖行政单位', areaList:['石河子市','阿拉尔市','图木舒克市','五家渠市']}
		]},
	{name:'河北', cityList:[
			{name:'石家庄市', areaList:['市辖区','长安区','桥东区','桥西区','新华区','井陉矿区','裕华区','井陉县','正定县','栾城县','行唐县','灵寿县','高邑县','深泽县','赞皇县','无极县','平山县','元氏县','赵　县','辛集市','藁城市','晋州市','新乐市','鹿泉市']},
			{name:'唐山市', areaList:['市辖区','路南区','路北区','古冶区','开平区','丰南区','丰润区','滦　县','滦南县','乐亭县','迁西县','玉田县','唐海县','遵化市','迁安市']},
			{name:'秦皇岛市', areaList:['市辖区','海港区','山海关区','北戴河区','青龙满族自治县','昌黎县','抚宁县','卢龙县']},
			{name:'邯郸市', areaList:['市辖区','邯山区','丛台区','复兴区','峰峰矿区','邯郸县','临漳县','成安县','大名县','涉　县','磁　县','肥乡县','永年县','邱　县','鸡泽县','广平县','馆陶县','魏　县','曲周县','武安市']},
			{name:'邢台市', areaList:['市辖区','桥东区','桥西区','邢台县','临城县','内丘县','柏乡县','隆尧县','任　县','南和县','宁晋县','巨鹿县','新河县','广宗县','平乡县','威　县','清河县','临西县','南宫市','沙河市']},
			{name:'保定市', areaList:['市辖区','新市区','北市区','南市区','满城县','清苑县','涞水县','阜平县','徐水县','定兴县','唐　县','高阳县','容城县','涞源县','望都县','安新县','易　县','曲阳县','蠡　县','顺平县','博野县','雄　县','涿州市','定州市','安国市','高碑店市']},
			{name:'张家口市', areaList:['市辖区','桥东区','桥西区','宣化区','下花园区','宣化县','张北县','康保县','沽源县','尚义县','蔚　县','阳原县','怀安县','万全县','怀来县','涿鹿县','赤城县','崇礼县']},
			{name:'承德市', areaList:['市辖区','双桥区','双滦区','鹰手营子矿区','承德县','兴隆县','平泉县','滦平县','隆化县','丰宁满族自治县','宽城满族自治县','围场满族蒙古族自治县']},
			{name:'沧州市', areaList:['市辖区','新华区','运河区','沧　县','青　县','东光县','海兴县','盐山县','肃宁县','南皮县','吴桥县','献　县','孟村回族自治县','泊头市','任丘市','黄骅市','河间市']},
			{name:'廊坊市', areaList:['市辖区','安次区','广阳区','固安县','永清县','香河县','大城县','文安县','大厂回族自治县','霸州市','三河市']},
			{name:'衡水市', areaList:['市辖区','桃城区','枣强县','武邑县','武强县','饶阳县','安平县','故城县','景　县','阜城县','冀州市','深州市']}
		]},
	{name:'山西', cityList:[
			{name:'太原市', areaList:['市辖区','小店区','迎泽区','杏花岭区','尖草坪区','万柏林区','晋源区','清徐县','阳曲县','娄烦县','古交市']},
			{name:'大同市', areaList:['市辖区','城　区','矿　区','南郊区','新荣区','阳高县','天镇县','广灵县','灵丘县','浑源县','左云县','大同县']},
			{name:'阳泉市', areaList:['市辖区','城　区','矿　区','郊　区','平定县','盂　县']},
			{name:'长治市', areaList:['市辖区','城　区','郊　区','长治县','襄垣县','屯留县','平顺县','黎城县','壶关县','长子县','武乡县','沁　县','沁源县','潞城市']},
			{name:'晋城市', areaList:['市辖区','城　区','沁水县','阳城县','陵川县','泽州县','高平市']},
			{name:'朔州市', areaList:['市辖区','朔城区','平鲁区','山阴县','应　县','右玉县','怀仁县']},
			{name:'晋中市', areaList:['市辖区','榆次区','榆社县','左权县','和顺县','昔阳县','寿阳县','太谷县','祁　县','平遥县','灵石县','介休市']},
			{name:'运城市', areaList:['市辖区','盐湖区','临猗县','万荣县','闻喜县','稷山县','新绛县','绛　县','垣曲县','夏　县','平陆县','芮城县','永济市','河津市']},
			{name:'忻州市', areaList:['市辖区','忻府区','定襄县','五台县','代　县','繁峙县','宁武县','静乐县','神池县','五寨县','岢岚县','河曲县','保德县','偏关县','原平市']},
			{name:'临汾市', areaList:['市辖区','尧都区','曲沃县','翼城县','襄汾县','洪洞县','古　县','安泽县','浮山县','吉　县','乡宁县','大宁县','隰　县','永和县','蒲　县','汾西县','侯马市','霍州市']},
			{name:'吕梁市', areaList:['市辖区','离石区','文水县','交城县','兴　县','临　县','柳林县','石楼县','岚　县','方山县','中阳县','交口县','孝义市','汾阳市']}
		]},
	{name:'内蒙古', cityList:[
			{name:'呼和浩特市', areaList:['市辖区','新城区','回民区','玉泉区','赛罕区','土默特左旗','托克托县','和林格尔县','清水河县','武川县']},
			{name:'包头市', areaList:['市辖区','东河区','昆都仑区','青山区','石拐区','白云矿区','九原区','土默特右旗','固阳县','达尔罕茂明安联合旗']},
			{name:'乌海市', areaList:['市辖区','海勃湾区','海南区','乌达区']},
			{name:'赤峰市', areaList:['市辖区','红山区','元宝山区','松山区','阿鲁科尔沁旗','巴林左旗','巴林右旗','林西县','克什克腾旗','翁牛特旗','喀喇沁旗','宁城县','敖汉旗']},
			{name:'通辽市', areaList:['市辖区','科尔沁区','科尔沁左翼中旗','科尔沁左翼后旗','开鲁县','库伦旗','奈曼旗','扎鲁特旗','霍林郭勒市']},
			{name:'鄂尔多斯市', areaList:['东胜区','达拉特旗','准格尔旗','鄂托克前旗','鄂托克旗','杭锦旗','乌审旗','伊金霍洛旗']},
			{name:'呼伦贝尔市', areaList:['市辖区','海拉尔区','阿荣旗','莫力达瓦达斡尔族自治旗','鄂伦春自治旗','鄂温克族自治旗','陈巴尔虎旗','新巴尔虎左旗','新巴尔虎右旗','满洲里市','牙克石市','扎兰屯市','额尔古纳市','根河市']},
			{name:'巴彦淖尔市', areaList:['市辖区','临河区','五原县','磴口县','乌拉特前旗','乌拉特中旗','乌拉特后旗','杭锦后旗']},
			{name:'乌兰察布市', areaList:['市辖区','集宁区','卓资县','化德县','商都县','兴和县','凉城县','察哈尔右翼前旗','察哈尔右翼中旗','察哈尔右翼后旗','四子王旗','丰镇市']},
			{name:'兴安盟', areaList:['乌兰浩特市','阿尔山市','科尔沁右翼前旗','科尔沁右翼中旗','扎赉特旗','突泉县']},
			{name:'锡林郭勒盟', areaList:['二连浩特市','锡林浩特市','阿巴嘎旗','苏尼特左旗','苏尼特右旗','东乌珠穆沁旗','西乌珠穆沁旗','太仆寺旗','镶黄旗','正镶白旗','正蓝旗','多伦县']},
			{name:'阿拉善盟', areaList:['阿拉善左旗','阿拉善右旗','额济纳旗']}
		]},
	{name:'江苏', cityList:[
			{name:'南京市', areaList:['市辖区','玄武区','白下区','秦淮区','建邺区','鼓楼区','下关区','浦口区','栖霞区','雨花台区','江宁区','六合区','溧水县','高淳县']},
			{name:'无锡市', areaList:['市辖区','崇安区','南长区','北塘区','锡山区','惠山区','滨湖区','江阴市','宜兴市']},
			{name:'徐州市', areaList:['市辖区','鼓楼区','云龙区','九里区','贾汪区','泉山区','丰　县','沛　县','铜山县','睢宁县','新沂市','邳州市']},
			{name:'常州市', areaList:['市辖区','天宁区','钟楼区','戚墅堰区','新北区','武进区','溧阳市','金坛市']},
			{name:'苏州市', areaList:['市辖区','沧浪区','平江区','金阊区','虎丘区','吴中区','相城区','常熟市','张家港市','昆山市','吴江市','太仓市']},
			{name:'南通市', areaList:['市辖区','崇川区','港闸区','海安县','如东县','启东市','如皋市','通州市','海门市']},
			{name:'连云港市', areaList:['市辖区','连云区','新浦区','海州区','赣榆县','东海县','灌云县','灌南县']},
			{name:'淮安市', areaList:['市辖区','清河区','楚州区','淮阴区','清浦区','涟水县','洪泽县','盱眙县','金湖县']},
			{name:'盐城市', areaList:['市辖区','亭湖区','盐都区','响水县','滨海县','阜宁县','射阳县','建湖县','东台市','大丰市']},
			{name:'扬州市', areaList:['市辖区','广陵区','邗江区','郊　区','宝应县','仪征市','高邮市','江都市']},
			{name:'镇江市', areaList:['市辖区','京口区','润州区','丹徒区','丹阳市','扬中市','句容市']},
			{name:'泰州市', areaList:['市辖区','海陵区','高港区','兴化市','靖江市','泰兴市','姜堰市']},
			{name:'宿迁市', areaList:['市辖区','宿城区','宿豫区','沭阳县','泗阳县','泗洪县']}
		]},
	{name:'浙江', cityList:[
			{name:'杭州市', areaList:['市辖区','上城区','下城区','江干区','拱墅区','西湖区','滨江区','萧山区','余杭区','桐庐县','淳安县','建德市','富阳市','临安市']},
			{name:'宁波市', areaList:['市辖区','海曙区','江东区','江北区','北仑区','镇海区','鄞州区','象山县','宁海县','余姚市','慈溪市','奉化市']},
			{name:'温州市', areaList:['市辖区','鹿城区','龙湾区','瓯海区','洞头县','永嘉县','平阳县','苍南县','文成县','泰顺县','瑞安市','乐清市']},
			{name:'嘉兴市', areaList:['市辖区','秀城区','秀洲区','嘉善县','海盐县','海宁市','平湖市','桐乡市']},
			{name:'湖州市', areaList:['市辖区','吴兴区','南浔区','德清县','长兴县','安吉县']},
			{name:'绍兴市', areaList:['市辖区','越城区','绍兴县','新昌县','诸暨市','上虞市','嵊州市']},
			{name:'金华市', areaList:['市辖区','婺城区','金东区','武义县','浦江县','磐安县','兰溪市','义乌市','东阳市','永康市']},
			{name:'衢州市', areaList:['市辖区','柯城区','衢江区','常山县','开化县','龙游县','江山市']},
			{name:'舟山市', areaList:['市辖区','定海区','普陀区','岱山县','嵊泗县']},
			{name:'台州市', areaList:['市辖区','椒江区','黄岩区','路桥区','玉环县','三门县','天台县','仙居县','温岭市','临海市']},
			{name:'丽水市', areaList:['市辖区','莲都区','青田县','缙云县','遂昌县','松阳县','云和县','庆元县','景宁畲族自治县','龙泉市']}
		]},
	{name:'安徽', cityList:[
			{name:'合肥市', areaList:['市辖区','瑶海区','庐阳区','蜀山区','包河区','长丰县','肥东县','肥西县']},
			{name:'芜湖市', areaList:['市辖区','镜湖区','马塘区','新芜区','鸠江区','芜湖县','繁昌县','南陵县']},
			{name:'蚌埠市', areaList:['市辖区','龙子湖区','蚌山区','禹会区','淮上区','怀远县','五河县','固镇县']},
			{name:'淮南市', areaList:['市辖区','大通区','田家庵区','谢家集区','八公山区','潘集区','凤台县']},
			{name:'马鞍山市', areaList:['市辖区','金家庄区','花山区','雨山区','当涂县']},
			{name:'淮北市', areaList:['市辖区','杜集区','相山区','烈山区','濉溪县']},
			{name:'铜陵市', areaList:['市辖区','铜官山区','狮子山区','郊　区','铜陵县']},
			{name:'安庆市', areaList:['市辖区','迎江区','大观区','郊　区','怀宁县','枞阳县','潜山县','太湖县','宿松县','望江县','岳西县','桐城市']},
			{name:'黄山市', areaList:['市辖区','屯溪区','黄山区','徽州区','歙　县','休宁县','黟　县','祁门县']},
			{name:'滁州市', areaList:['市辖区','琅琊区','南谯区','来安县','全椒县','定远县','凤阳县','天长市','明光市']},
			{name:'阜阳市', areaList:['市辖区','颍州区','颍东区','颍泉区','临泉县','太和县','阜南县','颍上县','界首市']},
			{name:'宿州市', areaList:['市辖区','墉桥区','砀山县','萧　县','灵璧县','泗　县']},
			{name:'巢湖市', areaList:['市辖区','居巢区','庐江县','无为县','含山县','和　县']},
			{name:'六安市', areaList:['市辖区','金安区','裕安区','寿　县','霍邱县','舒城县','金寨县','霍山县']},
			{name:'亳州市', areaList:['市辖区','谯城区','涡阳县','蒙城县','利辛县']},
			{name:'池州市', areaList:['市辖区','贵池区','东至县','石台县','青阳县']},
			{name:'宣城市', areaList:['市辖区','宣州区','郎溪县','广德县','泾　县','绩溪县','旌德县','宁国市']}
		]},
	{name:'福建', cityList:[
			{name:'福州市', areaList:['市辖区','鼓楼区','台江区','仓山区','马尾区','晋安区','闽侯县','连江县','罗源县','闽清县','永泰县','平潭县','福清市','长乐市']},
			{name:'厦门市', areaList:['市辖区','思明区','海沧区','湖里区','集美区','同安区','翔安区']},
			{name:'莆田市', areaList:['市辖区','城厢区','涵江区','荔城区','秀屿区','仙游县']},
			{name:'三明市', areaList:['市辖区','梅列区','三元区','明溪县','清流县','宁化县','大田县','尤溪县','沙　县','将乐县','泰宁县','建宁县','永安市']},
			{name:'泉州市', areaList:['市辖区','鲤城区','丰泽区','洛江区','泉港区','惠安县','安溪县','永春县','德化县','金门县','石狮市','晋江市','南安市']},
			{name:'漳州市', areaList:['市辖区','芗城区','龙文区','云霄县','漳浦县','诏安县','长泰县','东山县','南靖县','平和县','华安县','龙海市']},
			{name:'南平市', areaList:['市辖区','延平区','顺昌县','浦城县','光泽县','松溪县','政和县','邵武市','武夷山市','建瓯市','建阳市']},
			{name:'龙岩市', areaList:['市辖区','新罗区','长汀县','永定县','上杭县','武平县','连城县','漳平市']},
			{name:'宁德市', areaList:['市辖区','蕉城区','霞浦县','古田县','屏南县','寿宁县','周宁县','柘荣县','福安市','福鼎市']}
		]},
	{name:'江西', cityList:[
			{name:'南昌市', areaList:['市辖区','东湖区','西湖区','青云谱区','湾里区','青山湖区','南昌县','新建县','安义县','进贤县']},
			{name:'景德镇市', areaList:['市辖区','昌江区','珠山区','浮梁县','乐平市']},
			{name:'萍乡市', areaList:['市辖区','安源区','湘东区','莲花县','上栗县','芦溪县']},
			{name:'九江市', areaList:['市辖区','庐山区','浔阳区','九江县','武宁县','修水县','永修县','德安县','星子县','都昌县','湖口县','彭泽县','瑞昌市']},
			{name:'新余市', areaList:['市辖区','渝水区','分宜县']},
			{name:'鹰潭市', areaList:['市辖区','月湖区','余江县','贵溪市']},
			{name:'赣州市', areaList:['市辖区','章贡区','赣　县','信丰县','大余县','上犹县','崇义县','安远县','龙南县','定南县','全南县','宁都县','于都县','兴国县','会昌县','寻乌县','石城县','瑞金市','南康市']},
			{name:'吉安市', areaList:['市辖区','吉州区','青原区','吉安县','吉水县','峡江县','新干县','永丰县','泰和县','遂川县','万安县','安福县','永新县','井冈山市']},
			{name:'宜春市', areaList:['市辖区','袁州区','奉新县','万载县','上高县','宜丰县','靖安县','铜鼓县','丰城市','樟树市','高安市']},
			{name:'抚州市', areaList:['市辖区','临川区','南城县','黎川县','南丰县','崇仁县','乐安县','宜黄县','金溪县','资溪县','东乡县','广昌县']},
			{name:'上饶市', areaList:['市辖区','信州区','上饶县','广丰县','玉山县','铅山县','横峰县','弋阳县','余干县','鄱阳县','万年县','婺源县','德兴市']}
		]},
	{name:'山东', cityList:[
			{name:'济南市', areaList:['市辖区','历下区','市中区','槐荫区','天桥区','历城区','长清区','平阴县','济阳县','商河县','章丘市']},
			{name:'青岛市', areaList:['市辖区','市南区','市北区','四方区','黄岛区','崂山区','李沧区','城阳区','胶州市','即墨市','平度市','胶南市','莱西市']},
			{name:'淄博市', areaList:['市辖区','淄川区','张店区','博山区','临淄区','周村区','桓台县','高青县','沂源县']},
			{name:'枣庄市', areaList:['市辖区','市中区','薛城区','峄城区','台儿庄区','山亭区','滕州市']},
			{name:'东营市', areaList:['市辖区','东营区','河口区','垦利县','利津县','广饶县']},
			{name:'烟台市', areaList:['市辖区','芝罘区','福山区','牟平区','莱山区','长岛县','龙口市','莱阳市','莱州市','蓬莱市','招远市','栖霞市','海阳市']},
			{name:'潍坊市', areaList:['市辖区','潍城区','寒亭区','坊子区','奎文区','临朐县','昌乐县','青州市','诸城市','寿光市','安丘市','高密市','昌邑市']},
			{name:'济宁市', areaList:['市辖区','市中区','任城区','微山县','鱼台县','金乡县','嘉祥县','汶上县','泗水县','梁山县','曲阜市','兖州市','邹城市']},
			{name:'泰安市', areaList:['市辖区','泰山区','岱岳区','宁阳县','东平县','新泰市','肥城市']},
			{name:'威海市', areaList:['市辖区','环翠区','文登市','荣成市','乳山市']},
			{name:'日照市', areaList:['市辖区','东港区','岚山区','五莲县','莒　县']},
			{name:'莱芜市', areaList:['市辖区','莱城区','钢城区']},
			{name:'临沂市', areaList:['市辖区','兰山区','罗庄区','河东区','沂南县','郯城县','沂水县','苍山县','费　县','平邑县','莒南县','蒙阴县','临沭县']},
			{name:'德州市', areaList:['市辖区','德城区','陵　县','宁津县','庆云县','临邑县','齐河县','平原县','夏津县','武城县','乐陵市','禹城市']},
			{name:'聊城市', areaList:['市辖区','东昌府区','阳谷县','莘　县','茌平县','东阿县','冠　县','高唐县','临清市']},
			{name:'滨州市', areaList:['市辖区','滨城区','惠民县','阳信县','无棣县','沾化县','博兴县','邹平县']},
			{name:'荷泽市', areaList:['市辖区','牡丹区','曹　县','单　县','成武县','巨野县','郓城县','鄄城县','定陶县','东明县']}
		]},
	{name:'辽宁', cityList:[
			{name:'沈阳市', areaList:['市辖区','和平区','沈河区','大东区','皇姑区','铁西区','苏家屯区','东陵区','新城子区','于洪区','辽中县','康平县','法库县','新民市']},
			{name:'大连市', areaList:['市辖区','中山区','西岗区','沙河口区','甘井子区','旅顺口区','金州区','长海县','瓦房店市','普兰店市','庄河市']},
			{name:'鞍山市', areaList:['市辖区','铁东区','铁西区','立山区','千山区','台安县','岫岩满族自治县','海城市']},
			{name:'抚顺市', areaList:['市辖区','新抚区','东洲区','望花区','顺城区','抚顺县','新宾满族自治县','清原满族自治县']},
			{name:'本溪市', areaList:['市辖区','平山区','溪湖区','明山区','南芬区','本溪满族自治县','桓仁满族自治县']},
			{name:'丹东市', areaList:['市辖区','元宝区','振兴区','振安区','宽甸满族自治县','东港市','凤城市']},
			{name:'锦州市', areaList:['市辖区','古塔区','凌河区','太和区','黑山县','义　县','凌海市','北宁市']},
			{name:'营口市', areaList:['市辖区','站前区','西市区','鲅鱼圈区','老边区','盖州市','大石桥市']},
			{name:'阜新市', areaList:['市辖区','海州区','新邱区','太平区','清河门区','细河区','阜新蒙古族自治县','彰武县']},
			{name:'辽阳市', areaList:['市辖区','白塔区','文圣区','宏伟区','弓长岭区','太子河区','辽阳县','灯塔市']},
			{name:'盘锦市', areaList:['市辖区','双台子区','兴隆台区','大洼县','盘山县']},
			{name:'铁岭市', areaList:['市辖区','银州区','清河区','铁岭县','西丰县','昌图县','调兵山市','开原市']},
			{name:'朝阳市', areaList:['市辖区','双塔区','龙城区','朝阳县','建平县','喀喇沁左翼蒙古族自治县','北票市','凌源市']},
			{name:'葫芦岛市', areaList:['市辖区','连山区','龙港区','南票区','绥中县','建昌县','兴城市']}
		]},
	{name:'吉林', cityList:[
			{name:'长春市', areaList:['市辖区','南关区','宽城区','朝阳区','二道区','绿园区','双阳区','农安县','九台市','榆树市','德惠市']},
			{name:'吉林市', areaList:['市辖区','昌邑区','龙潭区','船营区','丰满区','永吉县','蛟河市','桦甸市','舒兰市','磐石市']},
			{name:'四平市', areaList:['市辖区','铁西区','铁东区','梨树县','伊通满族自治县','公主岭市','双辽市']},
			{name:'辽源市', areaList:['市辖区','龙山区','西安区','东丰县','东辽县']},
			{name:'通化市', areaList:['市辖区','东昌区','二道江区','通化县','辉南县','柳河县','梅河口市','集安市']},
			{name:'白山市', areaList:['市辖区','八道江区','抚松县','靖宇县','长白朝鲜族自治县','江源县','临江市']},
			{name:'松原市', areaList:['市辖区','宁江区','前郭尔罗斯蒙古族自治县','长岭县','乾安县','扶余县']},
			{name:'白城市', areaList:['市辖区','洮北区','镇赉县','通榆县','洮南市','大安市']},
			{name:'延边朝鲜族自治州', areaList:['延吉市','图们市','敦化市','珲春市','龙井市','和龙市','汪清县','安图县']}
		]},
	{name:'黑龙江', cityList:[
			{name:'哈尔滨市', areaList:['市辖区','道里区','南岗区','道外区','香坊区','动力区','平房区','松北区','呼兰区','依兰县','方正县','宾　县','巴彦县','木兰县','通河县','延寿县','阿城市','双城市','尚志市','五常市']},
			{name:'齐齐哈尔市', areaList:['市辖区','龙沙区','建华区','铁锋区','昂昂溪区','富拉尔基区','碾子山区','梅里斯达斡尔族区','龙江县','依安县','泰来县','甘南县','富裕县','克山县','克东县','拜泉县','讷河市']},
			{name:'鸡西市', areaList:['市辖区','鸡冠区','恒山区','滴道区','梨树区','城子河区','麻山区','鸡东县','虎林市','密山市']},
			{name:'鹤岗市', areaList:['市辖区','向阳区','工农区','南山区','兴安区','东山区','兴山区','萝北县','绥滨县']},
			{name:'双鸭山市', areaList:['市辖区','尖山区','岭东区','四方台区','宝山区','集贤县','友谊县','宝清县','饶河县']},
			{name:'大庆市', areaList:['市辖区','萨尔图区','龙凤区','让胡路区','红岗区','大同区','肇州县','肇源县','林甸县','杜尔伯特蒙古族自治县']},
			{name:'伊春市', areaList:['市辖区','伊春区','南岔区','友好区','西林区','翠峦区','新青区','美溪区','金山屯区','五营区','乌马河区','汤旺河区','带岭区','乌伊岭区','红星区','上甘岭区','嘉荫县','铁力市']},
			{name:'佳木斯市', areaList:['市辖区','永红区','向阳区','前进区','东风区','郊　区','桦南县','桦川县','汤原县','抚远县','同江市','富锦市']},
			{name:'七台河市', areaList:['市辖区','新兴区','桃山区','茄子河区','勃利县']},
			{name:'牡丹江市', areaList:['市辖区','东安区','阳明区','爱民区','西安区','东宁县','林口县','绥芬河市','海林市','宁安市','穆棱市']},
			{name:'黑河市', areaList:['市辖区','爱辉区','嫩江县','逊克县','孙吴县','北安市','五大连池市']},
			{name:'绥化市', areaList:['市辖区','北林区','望奎县','兰西县','青冈县','庆安县','明水县','绥棱县','安达市','肇东市','海伦市']},
			{name:'大兴安岭地区', areaList:['呼玛县','塔河县','漠河县']}
		]},
	{name:'海南', cityList:[
			{name:'海口市', areaList:['市辖区','秀英区','龙华区','琼山区','美兰区']},
			{name:'三亚市', areaList:['市辖区']},
			{name:'省直辖县级行政单位', areaList:['五指山市','琼海市','儋州市','文昌市','万宁市','东方市','定安县','屯昌县','澄迈县','临高县','白沙黎族自治县','昌江黎族自治县','乐东黎族自治县','陵水黎族自治县','保亭黎族苗族自治县','琼中黎族苗族自治县','西沙群岛','南沙群岛','中沙群岛的岛礁及其海域']}
		]},
	{name:'台湾', cityList:[
		]},
	{name:'香港', cityList:[
		]},
	{name:'澳门', cityList:[
		]}
];
window.provinceList = provinceList;