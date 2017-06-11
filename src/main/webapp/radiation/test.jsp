<html>
<head>
<title>hz-gb-2312 转码工具</title>
<style type="text/css">
html,body {
	font-size: 12px;
	margin: 0;
	overflow: hidden;
}

fieldset {
	padding: 5px;
	margin: 5px;
	text-align: center;
}
</style>
<script>
	window.resizeTo(480, 310);
	window.moveTo((screen.width - 480) / 2, (screen.height - 310) / 2);
	var $ = function(id) {
		return document.getElementById(id);
	};
	String.prototype.saveTo = function(path, cSet) {
		var out = new ActiveXObject("ADODB.Stream");
		with (out) {
			Type = 2;
			Open();
			CharSet = cSet || "GBK";
			Position = 0;
			WriteText = this;
			SaveToFile(path, 2);
			Close();
		}
		delete out;
	};
	String.loadFrom = function(path, cSet) {
		var ins = new ActiveXObject("ADODB.Stream");
		with (ins) {
			Type = 2;
			Mode = 3;
			Open();
			CharSet = cSet || "GBK";
			Position = 0;
			LoadFromFile(path);
		}
		var s = ins.ReadText();
		ins.Close();
		delete ins;
		return s;
	};
	function converEncoding(str, wcs, rcs) {
		var s = new ActiveXObject("ADODB.Stream");
		with (s) {
			Mode = 3;
			Type = 2;
			Open();
			CharSet = wcs;
			WriteText = str;
			Position = 0;
			CharSet = rcs;
			str = ReadText(-1);
			Close();
		}
		delete s;
		return str;
	}
	function encode(s) {
		return converEncoding(s, "hz-gb-2312", "gbk");
	}
	function decode(s) {
		return converEncoding(s, "gbk", "hz-gb-2312");
	}
	function doDecode() {
		$("txt").value = decode($('txt').value);
	}
	function doEncode() {
		$("txt").value = encode($('txt').value);
	}
</script>
</head>
<body>
	<fieldset>
		<legend>内容</legend>
		<textarea id="txt" style="width:100%" rows="14">请输入</textarea>
		<input type="button" onclick="doDecode();" value=" 解 码 " /><input
			type="button" onclick="doEncode();" value=" 转 码 " />
	</fieldset>
</body>
</html>