/** 
 * jsPDF AutoTable plugin v2.0.33
 * Copyright (c) 2014 Simon Bengtsson, https://github.com/simonbengtsson/jsPDF-AutoTable 
 * 
 * Licensed under the MIT License. 
 * http://opensource.org/licenses/mit-license 
 * 
 * @preserve 
 */
!function(t){"use strict";function e(){return{theme:"striped",styles:{},headerStyles:{},bodyStyles:{},alternateRowStyles:{},columnStyles:{},startY:!1,margin:40,pageBreak:"auto",tableWidth:"auto",createdHeaderCell:function(t,e){},createdCell:function(t,e){},drawHeaderRow:function(t,e){},drawRow:function(t,e){},drawHeaderCell:function(t,e){},drawCell:function(t,e){},beforePageContent:function(t){},afterPageContent:function(t){}}}function n(){return{cellPadding:5,fontSize:10,font:"helvetica",lineColor:200,lineWidth:.1,fontStyle:"normal",overflow:"ellipsize",fillColor:255,textColor:20,halign:"left",valign:"top",fillStyle:"F",rowHeight:20,columnWidth:"auto"}}function r(t){return t&&"object"==typeof t&&"default"in t?t.default:t}function o(t,e){return e={exports:{}},t(e,e.exports),e.exports}function i(t,e,n){t&&"object"===("undefined"==typeof t?"undefined":g(t))||console.error("The headers should be an object or array, is: "+("undefined"==typeof t?"undefined":g(t))),e&&"object"===("undefined"==typeof e?"undefined":g(e))||console.error("The data should be an object or array, is: "+("undefined"==typeof e?"undefined":g(e))),n&&"object"!==("undefined"==typeof n?"undefined":g(n))&&console.error("The data should be an object or array, is: "+("undefined"==typeof e?"undefined":g(e))),Array.prototype.forEach||console.error("The current browser does not support Array.prototype.forEach which is required for jsPDF-AutoTable. You can try polyfilling it by including this script https://developer.mozilla.org/en-US/docs/Web/JavaScript/Reference/Global_Objects/Array/forEach#Polyfill")}function f(t,e){pr=new m;var n=/\r\n|\r|\n/g,r=new x(t);r.index=-1;var o=P.styles([z[yr.theme].table,z[yr.theme].header]);r.styles=Object.assign({},o,yr.styles,yr.headerStyles),t.forEach(function(t,e){var o=e;"object"===("undefined"==typeof t?"undefined":g(t))&&(e="undefined"!=typeof t.dataKey?t.dataKey:t.key),"undefined"!=typeof t.width&&console.error("Use of deprecated option: column.width, use column.styles.columnWidth instead.");var i=new j(e,o);i.styles=yr.columnStyles[i.dataKey]||{},pr.columns.push(i);var f=new O;f.raw="object"===("undefined"==typeof t?"undefined":g(t))?t.title:t,f.styles=Object.assign({},r.styles),f.text=""+f.raw,f.contentWidth=2*f.styles.cellPadding+p(f.text,f.styles),f.text=f.text.split(n),r.cells[e]=f,yr.createdHeaderCell(f,{column:i,row:r,settings:yr})}),pr.headerRow=r,e.forEach(function(t,e){var r=new x(t),o=e%2===0,i=P.styles([z[yr.theme].table,o?z[yr.theme].alternateRow:{}]),f=Object.assign({},yr.styles,yr.bodyStyles,o?yr.alternateRowStyles:{});r.styles=Object.assign({},i,f),r.index=e,pr.columns.forEach(function(e){var o=new O;o.raw=t[e.dataKey],o.styles=Object.assign({},r.styles,e.styles),o.text="undefined"!=typeof o.raw?""+o.raw:"",r.cells[e.dataKey]=o,yr.createdCell(o,y({column:e,row:r})),o.contentWidth=2*o.styles.cellPadding+p(o.text,o.styles),o.text=o.text.split(n)}),pr.rows.push(r)})}function a(t,e){var n=0;pr.columns.forEach(function(t){t.contentWidth=pr.headerRow.cells[t.dataKey].contentWidth,pr.rows.forEach(function(e){var n=e.cells[t.dataKey].contentWidth;n>t.contentWidth&&(t.contentWidth=n)}),t.width=t.contentWidth,n+=t.contentWidth}),pr.contentWidth=n;var r=e-yr.margin.left-yr.margin.right,o=r;"number"==typeof yr.tableWidth?o=yr.tableWidth:"wrap"===yr.tableWidth&&(o=pr.contentWidth),pr.width=o<r?o:r;var i=[],f=0,a=pr.width/pr.columns.length,c=0;pr.columns.forEach(function(t){var e=P.styles([z[yr.theme].table,yr.styles,t.styles]);"wrap"===e.columnWidth?t.width=t.contentWidth:"number"==typeof e.columnWidth?t.width=e.columnWidth:("auto"===e.columnWidth,t.contentWidth<=a&&pr.contentWidth>pr.width?t.width=t.contentWidth:(i.push(t),f+=t.contentWidth,t.width=0)),c+=t.width}),u(i,c,f,a),pr.height=0;var l=pr.rows.concat(pr.headerRow);l.forEach(function(e,n){var r=0;pr.columns.forEach(function(n){var o=e.cells[n.dataKey];h(o.styles);var i=n.width-2*o.styles.cellPadding;if("linebreak"===o.styles.overflow)try{o.text=t.splitTextToSize(o.text,i+1,{fontSize:o.styles.fontSize})}catch(e){if(!(e instanceof TypeError&&Array.isArray(o.text)))throw e;o.text=t.splitTextToSize(o.text.join(" "),i+1,{fontSize:o.styles.fontSize})}else"ellipsize"===o.styles.overflow?o.text=b(o.text,i,o.styles):"visible"===o.styles.overflow||("hidden"===o.styles.overflow?o.text=b(o.text,i,o.styles,""):"function"==typeof o.styles.overflow?o.text=o.styles.overflow(o.text,i):console.error("Unrecognized overflow type: "+o.styles.overflow));var f=Array.isArray(o.text)?o.text.length-1:0;f>r&&(r=f)}),e.heightStyle=e.styles.rowHeight,e.height=e.heightStyle+r*e.styles.fontSize*S,pr.height+=e.height})}function u(t,e,n,r){for(var o=pr.width-e-n,i=0;i<t.length;i++){var f=t[i],a=f.contentWidth/n,c=f.contentWidth+o*a<r;if(o<0&&c){t.splice(i,1),n-=f.contentWidth,f.width=r,e+=f.width,u(t,e,n,r);break}f.width=f.contentWidth+o*a}}function c(t){yr.afterPageContent(y()),t(),pr.pageCount++,sr={x:yr.margin.left,y:yr.margin.top},yr.beforePageContent(y()),yr.drawHeaderRow(pr.headerRow,y({row:pr.headerRow}))!==!1&&d(pr.headerRow,yr.drawHeaderCell)}function l(t){var e=sr.y+t+yr.margin.bottom;return e>=hr.height}function s(t){pr.rows.forEach(function(e,n){if(l(e.height)){c(t)}e.y=sr.y,yr.drawRow(e,y({row:e}))!==!1&&d(e,yr.drawCell)})}function d(t,e){sr.x=yr.margin.left;for(var n=0;n<pr.columns.length;n++){var r=pr.columns[n],o=t.cells[r.dataKey];if(o){h(o.styles),o.x=sr.x,o.y=sr.y,o.height=t.height,o.width=r.width,"top"===o.styles.valign?o.textPos.y=sr.y+o.styles.cellPadding:"bottom"===o.styles.valign?o.textPos.y=sr.y+t.height-o.styles.cellPadding:o.textPos.y=sr.y+t.height/2,"right"===o.styles.halign?o.textPos.x=o.x+o.width-o.styles.cellPadding:"center"===o.styles.halign?o.textPos.x=o.x+o.width/2:o.textPos.x=o.x+o.styles.cellPadding;var i=y({column:r,row:t});e(o,i)!==!1&&(lr.rect(o.x,o.y,o.width,o.height,o.styles.fillStyle),lr.autoTableText(o.text,o.textPos.x,o.textPos.y,{halign:o.styles.halign,valign:o.styles.valign})),sr.x+=o.width}}sr.y+=t.height}function h(t){Object.keys(dr).forEach(function(e){var n=t[e],r=dr[e];"undefined"!=typeof n&&(n.constructor===Array?r.apply(this,n):r(n))})}function y(t){return Object.assign({pageCount:pr.pageCount,settings:yr,table:pr,doc:lr,cursor:sr},t||{})}function p(t,e){h(e);var n=lr.getStringUnitWidth(t);return n*e.fontSize}function b(t,e,n,r){if(r="undefined"!=typeof r?r:"...",Array.isArray(t))return t.forEach(function(o,i){t[i]=b(o,e,n,r)}),t;if(e>=p(t,n))return t;for(;e<p(t+r,n)&&!(t.length<2);)t=t.substring(0,t.length-1);return t.trim()+r}t="default"in t?t.default:t;var g="function"==typeof Symbol&&"symbol"==typeof Symbol.iterator?function(t){return typeof t}:function(t){return t&&"function"==typeof Symbol&&t.constructor===Symbol?"symbol":typeof t},v=function(t,e){if(!(t instanceof e))throw new TypeError("Cannot call a class as a function")},w=function(){function t(t,e){for(var n=0;n<e.length;n++){var r=e[n];r.enumerable=r.enumerable||!1,r.configurable=!0,"value"in r&&(r.writable=!0),Object.defineProperty(t,r.key,r)}}return function(e,n,r){return n&&t(e.prototype,n),r&&t(e,r),e}}(),m=function t(){v(this,t),this.height=0,this.width=0,this.contentWidth=0,this.rows=[],this.columns=[],this.headerRow=null,this.settings={},this.pageCount=1},x=function t(e){v(this,t),this.raw=e||{},this.index=0,this.styles={},this.cells={},this.height=0,this.y=0},O=function t(e){v(this,t),this.raw=e,this.styles={},this.text="",this.contentWidth=0,this.textPos={},this.height=0,this.width=0,this.x=0,this.y=0},j=function t(e,n){v(this,t),this.dataKey=e,this.index=n,this.options={},this.styles={},this.contentWidth=0,this.width=0,this.x=0},S=1.15,z={striped:{table:{fillColor:255,textColor:80,fontStyle:"normal",fillStyle:"F"},header:{textColor:255,fillColor:[41,128,185],rowHeight:23,fontStyle:"bold"},body:{},alternateRow:{fillColor:245}},grid:{table:{fillColor:255,textColor:80,fontStyle:"normal",lineWidth:.1,fillStyle:"DF"},header:{textColor:255,fillColor:[26,188,156],rowHeight:23,fillStyle:"F",fontStyle:"bold"},body:{},alternateRow:{}},plain:{header:{fontStyle:"bold"}}},P=function(){function t(){v(this,t)}return w(t,null,[{key:"initSettings",value:function(t){var n=Object.assign({},e(),t);"undefined"!=typeof n.extendWidth&&(n.tableWidth=n.extendWidth?"auto":"wrap",console.error("Use of deprecated option: extendWidth, use tableWidth instead.")),"undefined"!=typeof n.margins&&("undefined"==typeof n.margin&&(n.margin=n.margins),console.error("Use of deprecated option: margins, use margin instead.")),[["padding","cellPadding"],["lineHeight","rowHeight"],"fontSize","overflow"].forEach(function(t){var e="string"==typeof t?t:t[0],r="string"==typeof t?t:t[1];"undefined"!=typeof n[e]&&("undefined"==typeof n.styles[r]&&(n.styles[r]=n[e]),console.error("Use of deprecated option: "+e+", use the style "+r+" instead."))});var r=n.margin;return n.margin={},"number"==typeof r.horizontal&&(r.right=r.horizontal,r.left=r.horizontal),"number"==typeof r.vertical&&(r.top=r.vertical,r.bottom=r.vertical),["top","right","bottom","left"].forEach(function(t,e){if("number"==typeof r)n.margin[t]=r;else{var o=Array.isArray(r)?e:t;n.margin[t]="number"==typeof r[o]?r[o]:40}}),n}},{key:"styles",value:function(t){return t.unshift(n()),t.unshift({}),Object.assign.apply(this,t)}}]),t}(),E=o(function(t){var e=t.exports="undefined"!=typeof window&&window.Math==Math?window:"undefined"!=typeof self&&self.Math==Math?self:Function("return this")();"number"==typeof __g&&(__g=e)}),W=r(E),C=Object.freeze({default:W}),A=o(function(t){var e={}.hasOwnProperty;t.exports=function(t,n){return e.call(t,n)}}),T=r(A),F=Object.freeze({default:T}),k=o(function(t){t.exports=function(t){try{return!!t()}catch(t){return!0}}}),_=r(k),R=Object.freeze({default:_}),K=o(function(t){t.exports=!r(R)(function(){return 7!=Object.defineProperty({},"a",{get:function(){return 7}}).a})}),I=r(K),H=Object.freeze({default:I}),N=o(function(t){var e=t.exports={version:"2.4.0"};"number"==typeof __e&&(__e=e)}),M=r(N),U=N.version,Y=Object.freeze({default:M,version:U}),D=o(function(t){t.exports=function(t){return"object"==typeof t?null!==t:"function"==typeof t}}),B=r(D),J=Object.freeze({default:B}),G=o(function(t){var e=r(J);t.exports=function(t){if(!e(t))throw TypeError(t+" is not an object!");return t}}),q=r(G),L=Object.freeze({default:q}),Q=o(function(t){var e=r(J),n=r(C).document,o=e(n)&&e(n.createElement);t.exports=function(t){return o?n.createElement(t):{}}}),V=r(Q),X=Object.freeze({default:V}),Z=o(function(t){t.exports=!r(H)&&!r(R)(function(){return 7!=Object.defineProperty(r(X)("div"),"a",{get:function(){return 7}}).a})}),$=r(Z),tt=Object.freeze({default:$}),et=o(function(t){var e=r(J);t.exports=function(t,n){if(!e(t))return t;var r,o;if(n&&"function"==typeof(r=t.toString)&&!e(o=r.call(t)))return o;if("function"==typeof(r=t.valueOf)&&!e(o=r.call(t)))return o;if(!n&&"function"==typeof(r=t.toString)&&!e(o=r.call(t)))return o;throw TypeError("Can't convert object to primitive value")}}),nt=r(et),rt=Object.freeze({default:nt}),ot=o(function(t,e){var n=r(L),o=r(tt),i=r(rt),f=Object.defineProperty;e.f=r(H)?Object.defineProperty:function(t,e,r){if(n(t),e=i(e,!0),n(r),o)try{return f(t,e,r)}catch(t){}if("get"in r||"set"in r)throw TypeError("Accessors not supported!");return"value"in r&&(t[e]=r.value),t}}),it=r(ot),ft=ot.f,at=Object.freeze({default:it,f:ft}),ut=o(function(t){t.exports=function(t,e){return{enumerable:!(1&t),configurable:!(2&t),writable:!(4&t),value:e}}}),ct=r(ut),lt=Object.freeze({default:ct}),st=o(function(t){var e=r(at),n=r(lt);t.exports=r(H)?function(t,r,o){return e.f(t,r,n(1,o))}:function(t,e,n){return t[e]=n,t}}),dt=r(st),ht=Object.freeze({default:dt}),yt=o(function(t){var e=0,n=Math.random();t.exports=function(t){return"Symbol(".concat(void 0===t?"":t,")_",(++e+n).toString(36))}}),pt=r(yt),bt=Object.freeze({default:pt}),gt=o(function(t){var e=r(C),n=r(ht),o=r(F),i=r(bt)("src"),f="toString",a=Function[f],u=(""+a).split(f);r(Y).inspectSource=function(t){return a.call(t)},(t.exports=function(t,r,f,a){var c="function"==typeof f;c&&(o(f,"name")||n(f,"name",r)),t[r]!==f&&(c&&(o(f,i)||n(f,i,t[r]?""+t[r]:u.join(String(r)))),t===e?t[r]=f:a?t[r]?t[r]=f:n(t,r,f):(delete t[r],n(t,r,f)))})(Function.prototype,f,function(){return"function"==typeof this&&this[i]||a.call(this)})}),vt=r(gt),wt=Object.freeze({default:vt}),mt=o(function(t){t.exports=function(t){if("function"!=typeof t)throw TypeError(t+" is not a function!");return t}}),xt=r(mt),Ot=Object.freeze({default:xt}),jt=o(function(t){var e=r(Ot);t.exports=function(t,n,r){if(e(t),void 0===n)return t;switch(r){case 1:return function(e){return t.call(n,e)};case 2:return function(e,r){return t.call(n,e,r)};case 3:return function(e,r,o){return t.call(n,e,r,o)}}return function(){return t.apply(n,arguments)}}}),St=r(jt),zt=Object.freeze({default:St}),Pt=o(function(t){var e=r(C),n=r(Y),o=r(ht),i=r(wt),f=r(zt),a="prototype",u=function(t,r,c){var l,s,d,h,y=t&u.F,p=t&u.G,b=t&u.S,g=t&u.P,v=t&u.B,w=p?e:b?e[r]||(e[r]={}):(e[r]||{})[a],m=p?n:n[r]||(n[r]={}),x=m[a]||(m[a]={});p&&(c=r);for(l in c)s=!y&&w&&void 0!==w[l],d=(s?w:c)[l],h=v&&s?f(d,e):g&&"function"==typeof d?f(Function.call,d):d,w&&i(w,l,d,t&u.U),m[l]!=d&&o(m,l,h),g&&x[l]!=d&&(x[l]=d)};e.core=n,u.F=1,u.G=2,u.S=4,u.P=8,u.B=16,u.W=32,u.U=64,u.R=128,t.exports=u}),Et=r(Pt),Wt=Object.freeze({default:Et}),Ct=o(function(t){var e=r(bt)("meta"),n=r(J),o=r(F),i=r(at).f,f=0,a=Object.isExtensible||function(){return!0},u=!r(R)(function(){return a(Object.preventExtensions({}))}),c=function(t){i(t,e,{value:{i:"O"+ ++f,w:{}}})},l=function(t,r){if(!n(t))return"symbol"==typeof t?t:("string"==typeof t?"S":"P")+t;if(!o(t,e)){if(!a(t))return"F";if(!r)return"E";c(t)}return t[e].i},s=function(t,n){if(!o(t,e)){if(!a(t))return!0;if(!n)return!1;c(t)}return t[e].w},d=function(t){return u&&h.NEED&&a(t)&&!o(t,e)&&c(t),t},h=t.exports={KEY:e,NEED:!1,fastKey:l,getWeak:s,onFreeze:d}}),At=r(Ct),Tt=Ct.KEY,Ft=Ct.NEED,kt=Ct.fastKey,_t=Ct.getWeak,Rt=Ct.onFreeze,Kt=Object.freeze({default:At,KEY:Tt,NEED:Ft,fastKey:kt,getWeak:_t,onFreeze:Rt}),It=o(function(t){var e=r(C),n="__core-js_shared__",o=e[n]||(e[n]={});t.exports=function(t){return o[t]||(o[t]={})}}),Ht=r(It),Nt=Object.freeze({default:Ht}),Mt=o(function(t){var e=r(Nt)("wks"),n=r(bt),o=r(C).Symbol,i="function"==typeof o,f=t.exports=function(t){return e[t]||(e[t]=i&&o[t]||(i?o:n)("Symbol."+t))};f.store=e}),Ut=r(Mt),Yt=Object.freeze({default:Ut}),Dt=o(function(t){var e=r(at).f,n=r(F),o=r(Yt)("toStringTag");t.exports=function(t,r,i){t&&!n(t=i?t:t.prototype,o)&&e(t,o,{configurable:!0,value:r})}}),Bt=r(Dt),Jt=Object.freeze({default:Bt}),Gt=o(function(t,e){e.f=r(Yt)}),qt=r(Gt),Lt=Gt.f,Qt=Object.freeze({default:qt,f:Lt}),Vt=o(function(t){t.exports=!1}),Xt=r(Vt),Zt=Object.freeze({default:Xt}),$t=o(function(t){var e=r(C),n=r(Y),o=r(Zt),i=r(Qt),f=r(at).f;t.exports=function(t){var r=n.Symbol||(n.Symbol=o?{}:e.Symbol||{});"_"==t.charAt(0)||t in r||f(r,t,{value:i.f(t)})}}),te=r($t),ee=Object.freeze({default:te}),ne=o(function(t){var e={}.toString;t.exports=function(t){return e.call(t).slice(8,-1)}}),re=r(ne),oe=Object.freeze({default:re}),ie=o(function(t){var e=r(oe);t.exports=Object("z").propertyIsEnumerable(0)?Object:function(t){return"String"==e(t)?t.split(""):Object(t)}}),fe=r(ie),ae=Object.freeze({default:fe}),ue=o(function(t){t.exports=function(t){if(void 0==t)throw TypeError("Can't call method on  "+t);return t}}),ce=r(ue),le=Object.freeze({default:ce}),se=o(function(t){var e=r(ae),n=r(le);t.exports=function(t){return e(n(t))}}),de=r(se),he=Object.freeze({default:de}),ye=o(function(t){var e=Math.ceil,n=Math.floor;t.exports=function(t){return isNaN(t=+t)?0:(t>0?n:e)(t)}}),pe=r(ye),be=Object.freeze({default:pe}),ge=o(function(t){var e=r(be),n=Math.min;t.exports=function(t){return t>0?n(e(t),9007199254740991):0}}),ve=r(ge),we=Object.freeze({default:ve}),me=o(function(t){var e=r(be),n=Math.max,o=Math.min;t.exports=function(t,r){return t=e(t),t<0?n(t+r,0):o(t,r)}}),xe=r(me),Oe=Object.freeze({default:xe}),je=o(function(t){var e=r(he),n=r(we),o=r(Oe);t.exports=function(t){return function(r,i,f){var a,u=e(r),c=n(u.length),l=o(f,c);if(t&&i!=i){for(;c>l;)if(a=u[l++],a!=a)return!0}else for(;c>l;l++)if((t||l in u)&&u[l]===i)return t||l||0;return!t&&-1}}}),Se=r(je),ze=Object.freeze({default:Se}),Pe=o(function(t){var e=r(Nt)("keys"),n=r(bt);t.exports=function(t){return e[t]||(e[t]=n(t))}}),Ee=r(Pe),We=Object.freeze({default:Ee}),Ce=o(function(t){var e=r(F),n=r(he),o=r(ze)(!1),i=r(We)("IE_PROTO");t.exports=function(t,r){var f,a=n(t),u=0,c=[];for(f in a)f!=i&&e(a,f)&&c.push(f);for(;r.length>u;)e(a,f=r[u++])&&(~o(c,f)||c.push(f));return c}}),Ae=r(Ce),Te=Object.freeze({default:Ae}),Fe=o(function(t){t.exports="constructor,hasOwnProperty,isPrototypeOf,propertyIsEnumerable,toLocaleString,toString,valueOf".split(",")}),ke=r(Fe),_e=Object.freeze({default:ke}),Re=o(function(t){var e=r(Te),n=r(_e);t.exports=Object.keys||function(t){return e(t,n)}}),Ke=r(Re),Ie=Object.freeze({default:Ke}),He=o(function(t){var e=r(Ie),n=r(he);t.exports=function(t,r){for(var o,i=n(t),f=e(i),a=f.length,u=0;a>u;)if(i[o=f[u++]]===r)return o}}),Ne=r(He),Me=Object.freeze({default:Ne}),Ue=o(function(t,e){e.f=Object.getOwnPropertySymbols}),Ye=r(Ue),De=Ue.f,Be=Object.freeze({default:Ye,f:De}),Je=o(function(t,e){e.f={}.propertyIsEnumerable}),Ge=r(Je),qe=Je.f,Le=Object.freeze({default:Ge,f:qe}),Qe=o(function(t){var e=r(Ie),n=r(Be),o=r(Le);t.exports=function(t){var r=e(t),i=n.f;if(i)for(var f,a=i(t),u=o.f,c=0;a.length>c;)u.call(t,f=a[c++])&&r.push(f);return r}}),Ve=r(Qe),Xe=Object.freeze({default:Ve}),Ze=o(function(t){var e=r(oe);t.exports=Array.isArray||function(t){return"Array"==e(t)}}),$e=r(Ze),tn=Object.freeze({default:$e}),en=o(function(t){var e=r(at),n=r(L),o=r(Ie);t.exports=r(H)?Object.defineProperties:function(t,r){n(t);for(var i,f=o(r),a=f.length,u=0;a>u;)e.f(t,i=f[u++],r[i]);return t}}),nn=r(en),rn=Object.freeze({default:nn}),on=o(function(t){t.exports=r(C).document&&document.documentElement}),fn=r(on),an=Object.freeze({default:fn}),un=o(function(t){var e=r(L),n=r(rn),o=r(_e),i=r(We)("IE_PROTO"),f=function(){},a="prototype",u=function(){var t,e=r(X)("iframe"),n=o.length,i="<",f=">";for(e.style.display="none",r(an).appendChild(e),e.src="javascript:",t=e.contentWindow.document,t.open(),t.write(i+"script"+f+"document.F=Object"+i+"/script"+f),t.close(),u=t.F;n--;)delete u[a][o[n]];return u()};t.exports=Object.create||function(t,r){var o;return null!==t?(f[a]=e(t),o=new f,f[a]=null,o[i]=t):o=u(),void 0===r?o:n(o,r)}}),cn=r(un),ln=Object.freeze({default:cn}),sn=o(function(t,e){var n=r(Te),o=r(_e).concat("length","prototype");e.f=Object.getOwnPropertyNames||function(t){return n(t,o)}}),dn=r(sn),hn=sn.f,yn=Object.freeze({default:dn,f:hn}),pn=o(function(t){var e=r(he),n=r(yn).f,o={}.toString,i="object"==typeof window&&window&&Object.getOwnPropertyNames?Object.getOwnPropertyNames(window):[],f=function(t){try{return n(t)}catch(t){return i.slice()}};t.exports.f=function(t){return i&&"[object Window]"==o.call(t)?f(t):n(e(t))}}),bn=r(pn),gn=pn.f,vn=Object.freeze({default:bn,f:gn}),wn=o(function(t,e){var n=r(Le),o=r(lt),i=r(he),f=r(rt),a=r(F),u=r(tt),c=Object.getOwnPropertyDescriptor;e.f=r(H)?c:function(t,e){if(t=i(t),e=f(e,!0),u)try{return c(t,e)}catch(t){}if(a(t,e))return o(!n.f.call(t,e),t[e])}}),mn=r(wn),xn=wn.f,On=Object.freeze({default:mn,f:xn}),jn=o(function(t){var e=r(C),n=r(F),o=r(H),i=r(Wt),f=r(wt),a=r(Kt).KEY,u=r(R),c=r(Nt),l=r(Jt),s=r(bt),d=r(Yt),h=r(Qt),y=r(ee),p=r(Me),b=r(Xe),g=r(tn),v=r(L),w=r(he),m=r(rt),x=r(lt),O=r(ln),j=r(vn),S=r(On),z=r(at),P=r(Ie),E=S.f,W=z.f,A=j.f,T=e.Symbol,k=e.JSON,_=k&&k.stringify,K="prototype",I=d("_hidden"),N=d("toPrimitive"),M={}.propertyIsEnumerable,U=c("symbol-registry"),Y=c("symbols"),D=c("op-symbols"),B=Object[K],J="function"==typeof T,G=e.QObject,q=!G||!G[K]||!G[K].findChild,Q=o&&u(function(){return 7!=O(W({},"a",{get:function(){return W(this,"a",{value:7}).a}})).a})?function(t,e,n){var r=E(B,e);r&&delete B[e],W(t,e,n),r&&t!==B&&W(B,e,r)}:W,V=function(t){var e=Y[t]=O(T[K]);return e._k=t,e},X=J&&"symbol"==typeof T.iterator?function(t){return"symbol"==typeof t}:function(t){return t instanceof T},Z=function(t,e,r){return t===B&&Z(D,e,r),v(t),e=m(e,!0),v(r),n(Y,e)?(r.enumerable?(n(t,I)&&t[I][e]&&(t[I][e]=!1),r=O(r,{enumerable:x(0,!1)})):(n(t,I)||W(t,I,x(1,{})),t[I][e]=!0),Q(t,e,r)):W(t,e,r)},$=function(t,e){v(t);for(var n,r=b(e=w(e)),o=0,i=r.length;i>o;)Z(t,n=r[o++],e[n]);return t},tt=function(t,e){return void 0===e?O(t):$(O(t),e)},et=function(t){var e=M.call(this,t=m(t,!0));return!(this===B&&n(Y,t)&&!n(D,t))&&(!(e||!n(this,t)||!n(Y,t)||n(this,I)&&this[I][t])||e)},nt=function(t,e){if(t=w(t),e=m(e,!0),t!==B||!n(Y,e)||n(D,e)){var r=E(t,e);return!r||!n(Y,e)||n(t,I)&&t[I][e]||(r.enumerable=!0),r}},ot=function(t){for(var e,r=A(w(t)),o=[],i=0;r.length>i;)n(Y,e=r[i++])||e==I||e==a||o.push(e);return o},it=function(t){for(var e,r=t===B,o=A(r?D:w(t)),i=[],f=0;o.length>f;)!n(Y,e=o[f++])||r&&!n(B,e)||i.push(Y[e]);return i};J||(T=function(){if(this instanceof T)throw TypeError("Symbol is not a constructor!");var t=s(arguments.length>0?arguments[0]:void 0),e=function(r){this===B&&e.call(D,r),n(this,I)&&n(this[I],t)&&(this[I][t]=!1),Q(this,t,x(1,r))};return o&&q&&Q(B,t,{configurable:!0,set:e}),V(t)},f(T[K],"toString",function(){return this._k}),S.f=nt,z.f=Z,r(yn).f=j.f=ot,r(Le).f=et,r(Be).f=it,o&&!r(Zt)&&f(B,"propertyIsEnumerable",et,!0),h.f=function(t){return V(d(t))}),i(i.G+i.W+i.F*!J,{Symbol:T});for(var ft="hasInstance,isConcatSpreadable,iterator,match,replace,search,species,split,toPrimitive,toStringTag,unscopables".split(","),ut=0;ft.length>ut;)d(ft[ut++]);for(var ft=P(d.store),ut=0;ft.length>ut;)y(ft[ut++]);i(i.S+i.F*!J,"Symbol",{for:function(t){return n(U,t+="")?U[t]:U[t]=T(t)},keyFor:function(t){if(X(t))return p(U,t);throw TypeError(t+" is not a symbol!")},useSetter:function(){q=!0},useSimple:function(){q=!1}}),i(i.S+i.F*!J,"Object",{create:tt,defineProperty:Z,defineProperties:$,getOwnPropertyDescriptor:nt,getOwnPropertyNames:ot,getOwnPropertySymbols:it}),k&&i(i.S+i.F*(!J||u(function(){var t=T();return"[null]"!=_([t])||"{}"!=_({a:t})||"{}"!=_(Object(t))})),"JSON",{stringify:function(t){if(void 0!==t&&!X(t)){for(var e,n,r=[t],o=1;arguments.length>o;)r.push(arguments[o++]);return e=r[1],"function"==typeof e&&(n=e),!n&&g(e)||(e=function(t,e){if(n&&(e=n.call(this,t,e)),!X(e))return e}),r[1]=e,_.apply(k,r)}}}),T[K][N]||r(ht)(T[K],N,T[K].valueOf),l(T,"Symbol"),l(Math,"Math",!0),l(e.JSON,"JSON",!0)});r(jn);var Sn=o(function(t){var e=r(oe),n=r(Yt)("toStringTag"),o="Arguments"==e(function(){return arguments}()),i=function(t,e){try{return t[e]}catch(t){}};t.exports=function(t){var r,f,a;return void 0===t?"Undefined":null===t?"Null":"string"==typeof(f=i(r=Object(t),n))?f:o?e(r):"Object"==(a=e(r))&&"function"==typeof r.callee?"Arguments":a}}),zn=r(Sn),Pn=Object.freeze({default:zn}),En=o(function(t){var e=r(Pn),n={};n[r(Yt)("toStringTag")]="z",n+""!="[object z]"&&r(wt)(Object.prototype,"toString",function(){return"[object "+e(this)+"]"},!0)});r(En);var Wn=o(function(t){t.exports=r(Y).Symbol});r(Wn);var Cn=o(function(t){var e=r(Yt)("unscopables"),n=Array.prototype;void 0==n[e]&&r(ht)(n,e,{}),t.exports=function(t){n[e][t]=!0}}),An=r(Cn),Tn=Object.freeze({default:An}),Fn=o(function(t){t.exports=function(t,e){return{value:e,done:!!t}}}),kn=r(Fn),_n=Object.freeze({default:kn}),Rn=o(function(t){t.exports={}}),Kn=r(Rn),In=Object.freeze({default:Kn}),Hn=o(function(t){var e=r(ln),n=r(lt),o=r(Jt),i={};r(ht)(i,r(Yt)("iterator"),function(){return this}),t.exports=function(t,r,f){t.prototype=e(i,{next:n(1,f)}),o(t,r+" Iterator")}}),Nn=r(Hn),Mn=Object.freeze({default:Nn}),Un=o(function(t){var e=r(le);t.exports=function(t){return Object(e(t))}}),Yn=r(Un),Dn=Object.freeze({default:Yn}),Bn=o(function(t){var e=r(F),n=r(Dn),o=r(We)("IE_PROTO"),i=Object.prototype;t.exports=Object.getPrototypeOf||function(t){return t=n(t),e(t,o)?t[o]:"function"==typeof t.constructor&&t instanceof t.constructor?t.constructor.prototype:t instanceof Object?i:null}}),Jn=r(Bn),Gn=Object.freeze({default:Jn}),qn=o(function(t){var e=r(Zt),n=r(Wt),o=r(wt),i=r(ht),f=r(F),a=r(In),u=r(Mn),c=r(Jt),l=r(Gn),s=r(Yt)("iterator"),d=!([].keys&&"next"in[].keys()),h="@@iterator",y="keys",p="values",b=function(){return this};t.exports=function(t,r,g,v,w,m,x){u(g,r,v);var O,j,S,z=function(t){if(!d&&t in C)return C[t];switch(t){case y:return function(){return new g(this,t)};case p:return function(){return new g(this,t)}}return function(){return new g(this,t)}},P=r+" Iterator",E=w==p,W=!1,C=t.prototype,A=C[s]||C[h]||w&&C[w],T=A||z(w),F=w?E?z("entries"):T:void 0,k="Array"==r?C.entries||A:A;if(k&&(S=l(k.call(new t)),S!==Object.prototype&&(c(S,P,!0),e||f(S,s)||i(S,s,b))),E&&A&&A.name!==p&&(W=!0,T=function(){return A.call(this)}),e&&!x||!d&&!W&&C[s]||i(C,s,T),a[r]=T,a[P]=b,w)if(O={values:E?T:z(p),keys:m?T:z(y),entries:F},x)for(j in O)j in C||o(C,j,O[j]);else n(n.P+n.F*(d||W),r,O);return O}}),Ln=r(qn),Qn=Object.freeze({default:Ln}),Vn=o(function(t){var e=r(Tn),n=r(_n),o=r(In),i=r(he);t.exports=r(Qn)(Array,"Array",function(t,e){this._t=i(t),this._i=0,this._k=e},function(){var t=this._t,e=this._k,r=this._i++;return!t||r>=t.length?(this._t=void 0,n(1)):"keys"==e?n(0,r):"values"==e?n(0,t[r]):n(0,[r,t[r]])},"values"),o.Arguments=o.Array,e("keys"),e("values"),e("entries")});r(Vn);var Xn=o(function(t){t.exports=r(Y).Array.values});r(Xn);var Zn=o(function(t){var e=r(Ie),n=r(Be),o=r(Le),i=r(Dn),f=r(ae),a=Object.assign;t.exports=!a||r(R)(function(){var t={},e={},n=Symbol(),r="abcdefghijklmnopqrst";return t[n]=7,r.split("").forEach(function(t){e[t]=t}),7!=a({},t)[n]||Object.keys(a({},e)).join("")!=r})?function(t,r){for(var a=i(t),u=arguments.length,c=1,l=n.f,s=o.f;u>c;)for(var d,h=f(arguments[c++]),y=l?e(h).concat(l(h)):e(h),p=y.length,b=0;p>b;)s.call(h,d=y[b++])&&(a[d]=h[d]);return a}:a}),$n=r(Zn),tr=Object.freeze({default:$n}),er=o(function(t){var e=r(Wt);e(e.S+e.F,"Object",{assign:r(tr)})});r(er);var nr=o(function(t){t.exports=r(Y).Object.assign});r(nr);var rr=o(function(t){var e=r(Wt);e(e.S,"Array",{isArray:r(tn)})});r(rr);var or=o(function(t){t.exports=r(Y).Array.isArray});r(or);var ir=o(function(t){var e=r(Ie),n=r(he),o=r(Le).f;t.exports=function(t){return function(r){for(var i,f=n(r),a=e(f),u=a.length,c=0,l=[];u>c;)o.call(f,i=a[c++])&&l.push(t?[i,f[i]]:f[i]);return l}}}),fr=r(ir),ar=Object.freeze({default:fr}),ur=o(function(t){var e=r(Wt),n=r(ar)(!1);e(e.S,"Object",{values:function(t){return n(t)}})});r(ur);var cr=o(function(t){t.exports=r(Y).Object.values});r(cr);var lr,sr,dr,hr,yr,pr;t.API.autoTable=function(t,e,n){i(t,e,n),lr=this,hr=lr.internal.pageSize,dr={fillColor:lr.setFillColor,textColor:lr.setTextColor,fontStyle:lr.setFontStyle,lineColor:lr.setDrawColor,lineWidth:lr.setLineWidth,font:lr.setFont,fontSize:lr.setFontSize},yr=P.initSettings(n||{}),sr={x:yr.margin.left,y:yr.startY===!1?yr.margin.top:yr.startY};var r={textColor:30,fontSize:lr.internal.getFontSize(),fontStyle:lr.internal.getFont().fontStyle};f(t,e),a(this,hr.width);var o=pr.rows[0]&&"auto"===yr.pageBreak?pr.rows[0].height:0,u=yr.startY+yr.margin.bottom+pr.headerRow.height+o;return"avoid"===yr.pageBreak&&(u+=pr.height),("always"===yr.pageBreak&&yr.startY!==!1||yr.startY!==!1&&u>hr.height)&&(this.addPage(this.addPage),sr.y=yr.margin.top),h(r),yr.beforePageContent(y()),yr.drawHeaderRow(pr.headerRow,y({row:pr.headerRow}))!==!1&&d(pr.headerRow,yr.drawHeaderCell),h(r),s(this.addPage),yr.afterPageContent(y()),h(r),this},t.API.autoTableEndPosY=function(){return"undefined"==typeof sr||"undefined"==typeof sr.y?0:sr.y},t.API.autoTableHtmlToJson=function(t,e){e=e||!1;for(var n={},r=[],o=t.rows[0],i=0;i<o.cells.length;i++){var f=o.cells[i],a=window.getComputedStyle(f);(e||"none"!==a.display)&&(n[i]=f?f.textContent.trim():"")}for(var u=1;u<t.rows.length;u++){var c=t.rows[u],a=window.getComputedStyle(c);if(e||"none"!==a.display){var l=[],s=!0,d=!1,h=void 0;try{for(var y,p=Object.keys(n)[Symbol.iterator]();!(s=(y=p.next()).done);s=!0){var b=y.value,f=c.cells[b],g=f?f.textContent.trim():"";l.push(g)}}catch(t){d=!0,h=t}finally{try{!s&&p.return&&p.return()}finally{if(d)throw h}}r.push(l)}}return{columns:Object.values(n),rows:r,data:r}},t.API.autoTableAddPage=function(){c(lr.addPage)},t.API.autoTableText=function(t,e,n,r){"number"==typeof e&&"number"==typeof n||console.error("The x and y parameters are required. Missing for the text: ",t);var o=this.internal.getFontSize()/this.internal.scaleFactor,i=S,f=/\r\n|\r|\n/g,a=null,u=1;if("middle"!==r.valign&&"bottom"!==r.valign&&"center"!==r.halign&&"right"!==r.halign||(a="string"==typeof t?t.split(f):t,u=a.length||1),n+=o*(2-i),"middle"===r.valign?n-=u/2*o:"bottom"===r.valign&&(n-=u*o),"center"===r.halign||"right"===r.halign){var c=o;if("center"===r.halign&&(c*=.5),u>=1){for(var l=0;l<a.length;l++)this.text(a[l],e-this.getStringUnitWidth(a[l])*c,n),n+=o;return lr}e-=this.getStringUnitWidth(t)*c}return this.text(t,e,n),this}}(jsPDF);