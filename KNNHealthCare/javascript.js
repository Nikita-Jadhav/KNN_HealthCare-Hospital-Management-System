var req;
var isIE;
var searchId;
var completeTableDoctor;
var autoRowDoctor;
var searchMedicineId;
var completeTableMedicine;
var autoRowMedicine;

function start()
{
    init();
    initMedicine();
}

function init()
{
    searchId = document.getElementById("searchId");
    completeTableDoctor = document.getElementById("completeTableDoctor");
    autoRowDoctor = document.getElementById("autoRowDoctor");
}

function doCompletionDoctor()
{
    var url = "autocomplete?action=complete&searchId=" + escape(searchId.value);
    req = initRequest();
    req.open("GET", url, true);
    req.onreadystatechange = callback1;
    req.send(null);
}

function initRequest()
{
    if (window.XMLHttpRequest)
	{
        if (navigator.userAgent.indexOf('MSIE') != -1)
		{
            isIE = true;
        }
        return new XMLHttpRequest();
    }
	else if (window.ActiveXObject)
	{
        isIE = true;
        return new ActiveXObject("Microsoft.XMLHTTP");
    }
}

function callback1()
{
    clearTableD();
    if (req.readyState == 4)
	{
        if (req.status == 200)
		{
            parseMessages(req.responseXML);
        }
    }
}

function appendProduct1(productName,productId)
{
    var row;
    var cell;
    var linkElement;    
    if (isIE)
	{
        completeTableDoctor.style.display = 'block';
        row = completeTableDoctor.insertRow(completeTableDoctor.rows.length);
        cell = row.insertCell(0);
    }
	else
	{
        completeTableDoctor.style.display = 'table';
        row = document.createElement("tr");
        cell = document.createElement("td");
        row.appendChild(cell);
        completeTableDoctor.appendChild(row);
    }
    cell.className = "popupCell";
    linkElement = document.createElement("a");
    linkElement.className = "popupItem";
    linkElement.setAttribute("href", "autocomplete?action=lookup&searchId=" + productId);
    linkElement.appendChild(document.createTextNode(productName));
    cell.appendChild(linkElement);
	linkElement.style='color:black;font-size:15px;font-family:Century Schoolbook;';
}

function clearTableD()
{
    if (completeTableDoctor.getElementsByTagName("tr").length > 0)
	{
        completeTableDoctor.style.display = 'none';
        for (loop = completeTableDoctor.childNodes.length -1; loop >= 0 ; loop--)
		{
            completeTableDoctor.removeChild(completeTableDoctor.childNodes[loop]);
        }
    }
}

function parseMessages(responseXML)
{
    if (responseXML == null)
	{
        return false;
    }
	else
	{
        var products = responseXML.getElementsByTagName("doctors")[0];
        if (products.childNodes.length > 0)
		{
            completeTableDoctor.setAttribute("bordercolor", "black");
            completeTableDoctor.setAttribute("border", "1");    
            for (loop = 0; loop < products.childNodes.length; loop++)
			{
                var product = products.childNodes[loop];
                var productName = product.getElementsByTagName("doctorName")[0];
                var productId = product.getElementsByTagName("id")[0];
                appendProduct1(productName.childNodes[0].nodeValue,
                    productId.childNodes[0].nodeValue);
            }
        }
    }
}

function initMedicine()
{
    searchMedicineId = document.getElementById("searchMedicineId");
    completeTableMedicine = document.getElementById("completeTableMedicine");
    autoRowMedicine = document.getElementById("autoRowMedicine");
}

function doCompletionMedicine()
{
    var urlMedicine = "autocompleteMedicine?action=complete&searchMedicineId=" + escape(searchMedicineId.value);
    req = initRequestMedicine();
    req.open("GET", urlMedicine, true);
    req.onreadystatechange = callbackMedicine;
    req.send(null);
}

function initRequestMedicine()
{
    if (window.XMLHttpRequest)
	{
        if (navigator.userAgent.indexOf('MSIE') != -1)
		{
            isIE = true;
        }
        return new XMLHttpRequest();
    }
	else if (window.ActiveXObject)
	{
        isIE = true;
        return new ActiveXObject("Microsoft.XMLHTTP");
    }
}

function callbackMedicine()
{
    clearTableMedicine();
    if (req.readyState == 4)
	{
        if (req.status == 200)
		{
            parseMessagesMedicine(req.responseXML);
        }
    }
}

function appendProductMedicine(medicineName,medicineId)
{
    var row;
    var cell;
    var linkElement;    
    if (isIE)
	{
        completeTableMedicine.style.display = 'block';
        row = completeTableMedicine.insertRow(completeTableMedicine.rows.length);
        cell = row.insertCell(0);
    }
	else
	{
        completeTableMedicine.style.display = 'table';
        row = document.createElement("tr");
        cell = document.createElement("td");
        row.appendChild(cell);
        completeTableMedicine.appendChild(row);
    }
    cell.className = "popupCell";
    linkElement = document.createElement("a");
    linkElement.className = "popupItem";
    linkElement.setAttribute("href", "autocompleteMedicine?action=lookup&searchMedicineId=" + medicineId);
    linkElement.appendChild(document.createTextNode(medicineName));
    cell.appendChild(linkElement);
	linkElement.style='color:black;font-size:15px;font-family:Century Schoolbook;';
}

function clearTableMedicine()
{
    if (completeTableMedicine.getElementsByTagName("tr").length > 0)
	{
        completeTableMedicine.style.display = 'none';
        for (loop = completeTableMedicine.childNodes.length -1; loop >= 0 ; loop--)
		{
            completeTableMedicine.removeChild(completeTableMedicine.childNodes[loop]);
        }
    }
}

function parseMessagesMedicine(responseXML)
{
    if (responseXML == null)
	{
        return false;
    }
	else
	{
        var medicines = responseXML.getElementsByTagName("medicines")[0];
        if (medicines.childNodes.length > 0) {
            completeTableMedicine.setAttribute("bordercolor", "black");
            completeTableMedicine.setAttribute("border", "1");    
            for (loop = 0; loop < medicines.childNodes.length; loop++)
			{
                var medicineProduct = medicines.childNodes[loop];
                var medicineName = medicineProduct.getElementsByTagName("medicineName")[0];
                var medicineId = medicineProduct.getElementsByTagName("id")[0];
                appendProductMedicine(medicineName.childNodes[0].nodeValue,
                    medicineId.childNodes[0].nodeValue);
            }
        }
    }
}