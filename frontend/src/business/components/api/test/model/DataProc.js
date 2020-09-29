export {
  convert
}


// var obj1 = ["data", {
//   "requestId": "WS_RETENTION@@${aass}",
//   "projectId": 2,
//   "eventModel": 1,
//   "qp": "{\"events\":[{\"eventName\":\"player_upgrade\",\"eventDesc\":\"ç­çº§æå\",\"eventType\":\"event\",\"eventNameDisplay\":\"\",\"relation\":1,\"filts\":[{\"calcuSymbol\":\"C00\",\"columnDesc\":\"å½å®¶å°åº\",\"columnIndex\":9,\"columnName\":\"#country\",\"columnType\":\"varchar\",\"ftv\":[\"ä¸­å½\"],\"selectType\":\"string\",\"tableType\":\"0\",\"timeRelative\":\"\",\"timeUnit\":\"\"}],\"relationUser\":1,\"type\":0},{\"eventName\":\"player_upgrade\",\"eventDesc\":\"ç­çº§æå\",\"eventType\":\"event\",\"eventNameDisplay\":\"\",\"relation\":1,\"filts\":[{\"calcuSymbol\":\"C00\",\"columnDesc\":\"å½å®¶å°åº\",\"columnIndex\":9,\"columnName\":\"#country\",\"columnType\":\"varchar\",\"ftv\":[\"ä¸­å½\"],\"selectType\":\"string\",\"tableType\":\"0\",\"timeRelative\":\"\",\"timeUnit\":\"\"}],\"relationUser\":1,\"type\":1}],\"eventView\":{\"projectId\":2,\"startTime\":\"2020-08-14\",\"endTime\":\"2020-09-12\",\"recentDay\":\"1-30\",\"timeParticleSize\":\"T1\",\"unitNum\":\"7\",\"statType\":\"retention\",\"rtnRateOrNum\":\"R1\",\"graphShape\":\"L4\",\"groupBy\":[],\"uiCommonConfig\":\"{\\\"retentionLineType\\\":\\\"rate\\\",\\\"useRoi\\\":false}\",\"filts\":[{\"calcuSymbol\":\"C00\",\"columnDesc\":\"çä»½\",\"columnIndex\":10,\"columnName\":\"#province\",\"columnType\":\"varchar\",\"ftv\":[\"æ±èç\"],\"selectType\":\"string\",\"tableType\":\"0\",\"timeRelative\":\"\",\"timeUnit\":\"\",\"relativeEventTimeRetentionPhase\":null}],\"relation\":1}}",
//   "querySource": "module"
// }];
//
// convert(obj1);
// console.log("------end-----");
// console.log(obj1);
// //var obj22=eval("(" + obj1[1].qp + ")") ;
// //console.log(obj1 instanceof Array);
// //console.log(obj1[1].qp instanceof Object);
// //console.log(isJsonString(obj1[1].qp));
// //var myJSON = JSON.stringify(obj22);
// document.getElementById("demo").innerHTML = myJSON;

function convert(obj) {

  console.log("aaa------");
  console.log(obj);
  if (isJsonString(obj)) {
    obj = JSON.parse(obj);
  }
  if (obj instanceof Array) {
    console.log("bbb------");
    for (let index in obj) {
      if (isJsonString(obj[index])) {
        obj[index] = JSON.parse(obj[index]);
      }
      if (obj[index] instanceof Object) {
        console.log(obj[index]);
        convert(obj[index]);
      }
    }
  } else if (obj instanceof Object) {
    console.log("cc------");
    // console.log(obj);

    for (let k in obj) {
      if (isJsonString(obj[k])) {
        obj[k] = JSON.parse(obj[k]);
      }
      if (obj[k] instanceof Object) {
        console.log(obj[k]);
        convert(obj[k]);
      }
    }
  } else {
    return;
  }
}

function isJsonString(str) {
  try {
    if (typeof str === 'string' && typeof JSON.parse(str) == "object") {
      return true;
    }
  } catch (e) {
    console.log(" isJsonString e");
  }
  return false;
}
