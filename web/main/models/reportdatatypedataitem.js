/**
 * Created with IntelliJ IDEA.
 * User: marthinusswart
 * Date: 2013/10/23
 * Time: 5:52 PM
 * To change this template use File | Settings | File Templates.
 */

function ReportDataTypeDataItem(embeddedDataItem) {
    this.embeddedDataItem = embeddedDataItem;
    this.maxFieldNames = 6;

    this.showMessage = function () {
        return this.embeddedDataItem.name;
    }

    this.formatHeadingNames = function () {
        var headingNames = "";

        for (index in this.embeddedDataItem.headings) {
            var heading = this.embeddedDataItem.headings[index];

            if (index == 0) {
                headingNames = headingNames + heading.name;
            }
            else if (index < this.maxFieldNames) {
                headingNames = headingNames + ", " + heading.name;
            }
            else if (index == this.maxFieldNames) {
                headingNames = headingNames + ", ...";
            }
        }

        return headingNames;
    }


}

