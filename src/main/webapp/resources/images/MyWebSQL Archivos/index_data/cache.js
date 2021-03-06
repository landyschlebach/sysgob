/**
 * This file is a part of MyWebSQL package
 * @web        http://mywebsql.net
 * @license    http://mywebsql.net/license
 */

/*
  (c) 2008-2014 Samnan ur Rehman
 @web        http://mywebsql.net
 @license    http://mywebsql.net/license
*/
var db_mysql={quote:function(a){return-1==a.indexOf(".")?"`"+a+"`":"`"+a.replace(".","`.`")+"`"},escape:function(a){a=a.replace(/\\/g,"\\\\");return'"'+a.replace(/\"/g,'\\"')+'"'}};


/*
  (c) 2008-2014 Samnan ur Rehman
 @web        http://mywebsql.net
 @license    http://mywebsql.net/license
*/
function wrkfrmSubmit(a,b,e,f){document.frmquery?(frm=document.frmquery,$("#popup_overlay").removeClass("ui-helper-hidden")):(xfrm=getFrame(),frm=xfrm.document.frmquery,xfrm.onerror=frameErrorHandler,setPageStatus(!0));frm.type.value=a;frm.id.value=b;frm.name.value=e;frm.query.value=f;4>=arguments.length?frm.submit():(callback=arguments[4],data="q=wrkfrm&"+$(frm).serialize(),$.ajax({type:"POST",url:"?",data:data,success:callback}))}
function getFrame(){xfrm=null;return xfrm=$.browser.msie&&9>$.browser.version?document.frames("wrkfrm"):window.frames.wrkfrm}function resetFrame(){xfrm=getFrame();xfrm.src="javascript:false"}function debugMsg(a){$("#messageContainer").innerHTML=a}function frameErrorHandler(){setPageStatus(!1);$("#recordCounter").html("&nbsp;");$("#timeCounter").html("");$("#messageContainer").html("Navigation Error. Try reloading the page")}
function setPageStatus(a,b){a?($("#nav_bar").css("display","none"),$("#loader").css("display","table"),showNavBtns()):($("#loader").css("display","none"),$("#nav_bar").css("display","table-row"),showNavBtns("query","queryall"));b&&$("#messageContainer").html(b)}
function addCmdHistory(a){d=new Date;h=d.getHours();m=d.getMinutes();10>h&&(h="0"+h);10>m&&(m="0"+m);$("#sql-history > tbody:last").append('<tr><td valign="top" class="dt">['+h+":"+m+']</td><td class="hst">'+a+";</td></tr>");1<arguments.length&&1==arguments[1]&&(currentQuery=a)}function str_replace(a,b,e){e=""+e;var f=is_array(b),c=is_array(e);a=[].concat(a);b=[].concat(b);for(var g=(e=[].concat(e)).length;j=0,g--;)for(;e[g]=e[g].split(a[j]).join(f?b[j]||"":b[0]),++j in a;);return c?e:e[0]}
function is_array(a){return a instanceof Array}Array.indexOf||(Array.prototype.indexOf=function(a){for(var b=0;b<this.length;b++)if(this[b]==a)return b;return-1});function __(a){return window.lang&&window.lang[a]?window.lang[a]:a}function formatBytes(a){return 1024>a?a+" B":size=1048576>a?number_format(a/1024)+" KB":number_format(a/1048576,2)+" MB"}
function number_format(a,b,e,f){a=(a+"").replace(/[^0-9+\-Ee.]/g,"");a=isFinite(+a)?+a:0;b=isFinite(+b)?Math.abs(b):0;f="undefined"===typeof f?",":f;e="undefined"===typeof e?".":e;var c="",c=function(a,b){var c=Math.pow(10,b);return""+Math.round(a*c)/c},c=(b?c(a,b):""+Math.round(a)).split(".");3<c[0].length&&(c[0]=c[0].replace(/\B(?=(?:\d{3})+(?!\d))/g,f));(c[1]||"").length<b&&(c[1]=c[1]||"",c[1]+=Array(b-c[1].length+1).join("0"));return c.join(e)}
function htmlchars(a,b,e,f){var c=e=0,g=!1;if("undefined"===typeof b||null===b)b=2;a=a.toString();!1!==f&&(a=a.replace(/&/g,"&amp;"));a=a.replace(/</g,"&lt;").replace(/>/g,"&gt;");f={ENT_NOQUOTES:0,ENT_HTML_QUOTE_SINGLE:1,ENT_HTML_QUOTE_DOUBLE:2,ENT_COMPAT:2,ENT_QUOTES:3,ENT_IGNORE:4};0===b&&(g=!0);if("number"!==typeof b){b=[].concat(b);for(c=0;c<b.length;c++)0===f[b[c]]?g=!0:f[b[c]]&&(e|=f[b[c]]);b=e}b&f.ENT_HTML_QUOTE_SINGLE&&(a=a.replace(/'/g,"&#039;"));g||(a=a.replace(/"/g,"&quot;"));return a}
function uiShowStatus(a,b,e,f){var c=$(a).data("status");if(!c)return c=window.setTimeout(function(){uiShowStatus(a,b,e,f)},f),$(a).data("status",c),!0;$.ajax({type:"GET",url:"status.php?type="+b+"&id="+e,success:function(g){g&&g.c&&($(a).progressbar("value",g.c),100<=g.c&&($(a).progressbar("destory"),$(a).removeData("status")));c=window.setTimeout(function(){uiShowStatus(a,b,e,f)},f);$(a).data("status",c)},error:function(){$(a).progressbar("destory");$(a).removeData("status")},dataType:"json"})}
function uiShowObjectList(a,b,e,f){if(!a.length){new_list=[];for(var c in a)for(j=0;j<a[c].length;j++)new_list.push(c+"."+a[c][j]);a=new_list}html="";for(c=0;c<a.length;c++)table=a[c],id=str_replace(/[\s\"']/,"",table),value=str_replace(/[\"]/,"&quot",table),html+='<div class="obj"><input'+(f?"":' checked="checked"')+' type="checkbox" name="'+b+'[]" id="'+b+"_"+id+'" value="'+value+'" /><label class="right" for="'+b+"_"+id+'">'+table+"</label></div>";""!=html&&(html='<div class="objhead ui-widget-header"><input'+
(f?"":' checked="checked"')+' type="checkbox" class="selectall" id="h_'+e+'" /><label class="right" for="h_'+e+'">'+e+'</label><span class="toggler">&#x25B4;</span></div><div>'+html+"</div>",$("#db_objects").append(html))};


