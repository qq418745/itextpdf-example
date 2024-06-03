package com.itextpdf;

public class TemplateFiller {
    // 数据类
    public static class PaymentData {
        String room;
        String orderNumber;
        String paymentType;
        String orderPayType;
        String billPeriods;
        double amount;
        String note;
        String paymentDate;
        String printDate;

        public PaymentData(String room, String orderNumber, String paymentType, String orderPayType,
                           String billPeriods, double amount, String note, String paymentDate, String printDate) {
            this.room = room;
            this.orderNumber = orderNumber;
            this.paymentType = paymentType;
            this.orderPayType = orderPayType;
            this.billPeriods = billPeriods;
            this.amount = amount;
            this.note = note;
            this.paymentDate = paymentDate;
            this.printDate = printDate;
        }
    }

    public static String replacePlaceholders(String template, PaymentData data) {
        return template
                .replace("{{room}}", data.room)
                .replace("{{orderNumber}}", data.orderNumber)
                .replace("{{paymentType}}", data.paymentType)
                .replace("{{orderPayType}}", data.orderPayType)
                .replace("{{billPeriods}}", data.billPeriods)
                .replace("{{amount}}", String.valueOf(data.amount))
                .replace("{{note}}", data.note)
                .replace("{{paymentDate}}", data.paymentDate)
                .replace("{{printDate}}", data.printDate);
    }
}
