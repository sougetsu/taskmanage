(function ($) {
	$.fn.extend({
		editDialog: function (options) {
			var defaultOptions = {
                width : dialog_width,
                height : dialog_height,
                title : null,
                btnText : '保存',
                iconCls : "icon-edit",
                onSuccess : function(data){ alert("操作成功"); },
                onFaild : function(data){ alert(data.ErrorMessage); },
                onClose : function(){
                	var d = $(this).closest('.window-body');
					d.dialog('destroy');
                },
                buttons : [{
			        text:function(){
			        	if (options.btnText != undefined && options.btnText != null){
			        		return options.btnText;
			        	}
			        	return '保存';
			        },
                    iconCls:'icon-save',
			        handler:function(){
			        	var d = $(this).closest('.window-body');
                        submitDialogForm(d);
                    }
		        },{
                    text:'关闭',
                    iconCls:'icon-cancel',
			        handler:function(){
			        	var d = $(this).closest('.window-body');
						d.dialog('destroy');
                    }
                }]
            };
			function makeEditDialog(options) {
				return $('<div/>').dialog({
                    title: options.title,  
                    width: options.width,  
                    height: options.height,  
                    closed: false,  
                    cache: false, 
                    resizable: true,
                    iconCls: options.iconCls, 
                    href: options.url,  
                    buttons:options.buttons,
                    modal:true,
                    onLoad : function() {
                    	if (options.onLoad != undefined && options.onLoad != null)
                        {
                    		options.onLoad(options.id);
                        }
                    },
                    onClose : options.onClose
                }); 
			};
            function submitDialogForm(d)
            {
            	$(d).find('form').form('submit', {
                    url: $(this).attr("action"),  
                    success:function(result){  
                    	var r = $.parseJSON(result);
						if (r.success) {
							d.dialog('destroy');
							if (options.originContain != undefined && options.originContain != null)
	                        {
								$(options.originContain).datagrid('reload');
	                        }
							if (options.treeContain != undefined && options.treeContain != null)
	                        {
								$(options.treeContain).treegrid('reload');
	                        }
						}
						$.messager.show({
							title : '提示',
							msg : r.msg
						});
                    }  
                });
            }
			return this.each(function () {
                var optionsValue = $.extend(true, {}, defaultOptions, options );
                
				makeEditDialog(optionsValue);
			});
		},
		detailDialog: function (options) {
			var defaultOptions = {
                width : dialog_width,
                height : dialog_height,
                title : null,
                iconCls : "icon-edit",
                onSuccess : function(data){ alert("操作成功"); },
                onFaild : function(data){ alert(data.ErrorMessage); },
                onClose : function(){
                	var d = $(this).closest('.window-body');
					d.dialog('destroy');
                },
                buttons : [{
                    text:'关闭',
                    iconCls:'icon-cancel',
			        handler:function(){
			        	var d = $(this).closest('.window-body');
						d.dialog('destroy');
                    }
                }]
            };
			function makeDetailDialog(options) {
				return $('<div/>').dialog({
                    title: options.title,  
                    width: options.width,  
                    height: options.height,  
                    closed: false,  
                    cache: false, 
                    resizable: true,
                    iconCls: options.iconCls, 
                    href: options.url,  
                    modal: true,
                    buttons:options.buttons,
                    onLoad : function() {
                    	if (options.onLoad != undefined && options.onLoad != null)
                        {
                    		options.onLoad(options.id);
                        }
                    },
                    onClose : options.onClose
                }); 
			};
			return this.each(function () {
                var optionsValue = $.extend(true, {}, defaultOptions, options );
                makeDetailDialog(optionsValue);
			});
		},
		verifyDialog: function (options) {
			var defaultOptions = {
	                width : dialog_width,
	                height : dialog_height,
	                title : null,
	                iconCls : "icon-edit",
	                onSuccess : function(data){ alert("操作成功"); },
	                onFaild : function(data){ alert(data.ErrorMessage); },
	                onClose : function(){
	                	var d = $(this).closest('.window-body');
						d.dialog('destroy');
	                }
	                
            };
			function makeVerifyDialog(options) {
				return $('<div/>').dialog({
                    title: options.title,  
                    width: options.width,  
                    height: options.height,  
                    closed: false,  
                    cache: false, 
                    resizable: true,
                    iconCls: options.iconCls, 
                    href: options.url,  
                    modal: true,
                    buttons:[{
                        text:options.btnText1,
                        iconCls:'icon-save',
				        handler:function(){
				        	var d = $(this).closest('.window-body');
	                        submitDialogForm(d,options.btnUrl1);
                        }
                    },{
                    	text:options.btnText2,
                        iconCls:'icon-save',
				        handler:function(){
				        	var d = $(this).closest('.window-body');
				        	submitDialogForm(d,options.btnUrl2);
                        }
                    },{
                        text:'关闭',
                        iconCls:'icon-cancel',
				        handler:function(){
				        	var d = $(this).closest('.window-body');
							d.dialog('destroy');
                        }
                    }],
                    onLoad : function() {
                    	if (options.onLoad != undefined && options.onLoad != null)
                        {
                    		options.onLoad(options.id);
                        }
                    },
                    onClose : options.onClose
                }); 
			};
            function submitDialogForm(d,url)
            {
            	var submitForm = $(d).find('form');
            	if(submitForm.form('validate')){
            		$.ajax({
                        type: "POST",
                        url:url,
                        data:submitForm.serialize(),
                        dataType : 'json',
						success : function(result) {
							if (result.success) {
								d.dialog('destroy');
								if (options.originContain != undefined && options.originContain != null)
		                        {
									$(options.originContain).datagrid('reload');
		                        }
								if (options.treeContain != undefined && options.treeContain != null)
		                        {
									$(options.treeContain).treegrid('reload');
		                        }
							}
							$.messager.show({
								title : '提示',
								msg : result.msg
							});
						}
                    });
            	}
            }
			return this.each(function () {
                var optionsValue = $.extend(true, {}, defaultOptions, options );
                makeVerifyDialog(optionsValue);
			});
		},
		
		verifyDialog_dsh: function (options) {
			var defaultOptions = {
	                width : dialog_width,
	                height : dialog_height,
	                title : null,
	                iconCls : "icon-edit",
	                onSuccess : function(data){ alert("操作成功"); },
	                onFaild : function(data){ alert(data.ErrorMessage); },
	                onClose : function(){
	                	var d = $(this).closest('.window-body');
						d.dialog('destroy');
	                }
            };
			function makeVerifyDialog(options) {
				return $('<div/>').dialog({
                    title: options.title,  
                    width: options.width,  
                    height: options.height,  
                    closed: false,  
                    cache: false, 
                    resizable: true,
                    iconCls: options.iconCls, 
                    href: options.url,  
                    modal: true,
                    buttons:[{
                        text:options.btnText1,
                        iconCls:'icon-save',
				        handler:function(){
				        	var d = $(this).closest('.window-body');
	                        submitDialogForm(d,options.btnUrl1);
                        }
                    },{
                    	text:options.btnText2,
                        iconCls:'icon-save',
				        handler:function(){
							var nopass_advice = document.getElementById("nopasscontent");
							alert(nopass_advice.value=="");
							if(nopass_advice.value==""){
								$.messager.show({
									title : '提示',
									msg : '请填写不通过原因'
								});
							}else{
								var d = $(this).closest('.window-body');
				        		submitDialogForm(d,options.btnUrl2);	
							}
                        }
                    },{
                        text:'关闭',
                        iconCls:'icon-cancel',
				        handler:function(){
				        	var d = $(this).closest('.window-body');
							d.dialog('destroy');
                        }
                    }],
                    onLoad : function() {
                    	if (options.onLoad != undefined && options.onLoad != null)
                        {
                    		options.onLoad(options.id);
                        }
                    },
                    onClose : options.onClose
                }); 
			};
            function submitDialogForm(d,url)
            {
            	var submitForm = $(d).find('form');
            	if(submitForm.form('validate')){
            		$.ajax({
                        type: "POST",
                        url:url,
                        data:submitForm.serialize(),
                        dataType : 'json',
						success : function(result) {
							if (result.success) {
								d.dialog('destroy');
								if (options.originContain != undefined && options.originContain != null)
		                        {
									$(options.originContain).datagrid('reload');
		                        }
								if (options.treeContain != undefined && options.treeContain != null)
		                        {
									$(options.treeContain).treegrid('reload');
		                        }
							}
							$.messager.show({
								title : '提示',
								msg : result.msg
							});
						}
                    });
            	}
            }
			return this.each(function () {
                var optionsValue = $.extend(true, {}, defaultOptions, options );
                makeVerifyDialog(optionsValue);
			});
		},
		
		distributeDialog: function (options) {
			var defaultOptions = {
	                width : dialog_width,
	                height : dialog_height,
	                title : null,
	                iconCls : "icon-edit",
	                onSuccess : function(data){ alert("操作成功"); },
	                onFaild : function(data){ alert(data.ErrorMessage); },
	                onClose : function(){
	                	var d = $(this).closest('.window-body');
						d.dialog('destroy');
	                }
            };
			function makeVerifyDialog(options) {
				return $('<div/>').dialog({
                    title: options.title,  
                    width: options.width,  
                    height: options.height,  
                    closed: false,  
                    cache: false, 
                    resizable: true,
                    iconCls: options.iconCls, 
                    href: options.url,  
                    modal: true,
                    buttons:[{
                        text:options.btnText1,
                        iconCls:'icon-save',
				        handler:function(){
				        	var d = $(this).closest('.window-body');
	                        submitDialogForm(d,options.btnUrl1);
                        }
                    },{
                        text:'关闭',
                        iconCls:'icon-cancel',
				        handler:function(){
				        	var d = $(this).closest('.window-body');
							d.dialog('destroy');
                        }
                    }],
                    onLoad : function() {
                    	if (options.onLoad != undefined && options.onLoad != null)
                        {
                    		options.onLoad(options.id);
                        }
                    },
                    onClose : options.onClose
                }); 
			};
            function submitDialogForm(d,url)
            {
            	var submitForm = $(d).find('form');
            	if(submitForm.form('validate')){
            		$.ajax({
                        type: "POST",
                        url:url,
                        data:submitForm.serialize(),
                        dataType : 'json',
						success : function(result) {
							if (result.success) {
								d.dialog('destroy');
								if (options.originContain != undefined && options.originContain != null)
		                        {
									$(options.originContain).datagrid('reload');
		                        }
								if (options.treeContain != undefined && options.treeContain != null)
		                        {
									$(options.treeContain).treegrid('reload');
		                        }
							}
							$.messager.show({
								title : '提示',
								msg : result.msg
							});
						}
                    });
            	}
            }
			return this.each(function () {
                var optionsValue = $.extend(true, {}, defaultOptions, options );
                makeVerifyDialog(optionsValue);
			});
		},
		formSubmit : function(options){
			var d = $(this).closest('.window-body');
			return $(this).form('submit', {
				url : options.url,
				success : function(result) {
					try {
						var r = $.parseJSON(result);
						if (r.success) {
							d.dialog('destroy');
							$(options.datagridtable).datagrid('reload');
						}
						$.messager.show({
							title : '提示',
							msg : r.msg
						});
					} catch (e) {
						$.messager.alert('提示', result);
					}
				}
			});
		},
		tabformSubmit : function(options){
			return $(this).form('submit', {
				url : options.url,
				success : function(result) {
					try {
						var r = $.parseJSON(result);
						if (r.success) {
							var index = $('#layout_center_tabs').tabs('getTabIndex', $('#layout_center_tabs').tabs('getSelected'));
							var tab = $('#layout_center_tabs').tabs('getTab', index);
							$('#layout_center_tabs').tabs('close', index);
							$(options.datagridtable).datagrid('reload');
						}
						$.messager.show({
							title : '提示',
							msg : r.msg
						});
					} catch (e) {
						$.messager.alert('提示', result);
					}
				}
			});
		}	
	});
})(jQuery);