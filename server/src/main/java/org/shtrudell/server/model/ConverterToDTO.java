package org.shtrudell.server.model;

import org.shtrudell.common.model.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class ConverterToDTO {

    public static AuthorDTO convertAuthor(Author author) {
        if(author == null) return null;
        return AuthorDTO.builder().
                id(author.getId()).
                name(author.getName()).
                surname(author.getSurname()).
                patronymic(author.getSurname()).
                build();
    }

    public static DocnameDTO convertDocname(Docname docname) {
        if(docname == null) return null;
        return DocnameDTO.builder().
                id(docname.getId()).
                title(docname.getTitle()).
                isbn(docname.getIsbn()).
                edition(docname.getEdition()).
                releaseDate(docname.getReleaseDate()).
                author(convertAuthor(docname.getAuthor())).
                build();

    }
    public static DocumentDTO convertDocument(Document document) {
        if(document == null) return null;
        return DocumentDTO.builder().
                id(document.getId()).
                name(convertDocname(document.getName())).
                build();
    }

    public static List<DocumentDTO> convertDocuments(Collection<Document> documents) {
        if(documents == null) return null;

        List<DocumentDTO> returnValue = new ArrayList<>();

        for (var document : documents) {
            returnValue.add(convertDocument(document));
        }
        return returnValue;
    }

    public static FundDTO convertFund(Fund fund) {
        if(fund == null) return null;
        return FundDTO.builder().
                id(fund.getId()).
                name(fund.getName()).
                documents(convertDocuments(fund.getDocuments())).
                build();
    }

    public static List<FundDTO> convertFunds(Collection<Fund> funds) {
        if(funds == null) return null;

        List<FundDTO> returnValue = new ArrayList<>();

        for (var fund : funds) {
            returnValue.add(convertFund(fund));
        }
        return returnValue;
    }

    public static SimpleFundDTO convertSimpleFund(Fund fund) {
        if(fund == null) return null;
        return SimpleFundDTO.builder().
                id(fund.getId()).
                name(fund.getName()).
                build();
    }

    public static List<SimpleFundDTO> convertSimpleFunds(Collection<Fund> funds) {
        if(funds == null) return null;

        List<SimpleFundDTO> returnValue = new ArrayList<>();

        for (var fund : funds) {
            returnValue.add(convertSimpleFund(fund));
        }
        return returnValue;
    }

    public static ReceiptDTO convertReceipt(Receipt receipt) {
        if(receipt == null) return null;
        return ReceiptDTO.builder().
                id(receipt.getId()).
                date(receipt.getDate()).
                fund(convertFund(receipt.getFund())).
                user(convertUser(receipt.getUser())).
                documents(convertDocuments(receipt.getDocuments())).
                build();
    }

    public static List<ReceiptDTO> convertReceipts(Collection<Receipt> receipts) {
        if(receipts == null) return null;

        List<ReceiptDTO> returnValue = new ArrayList<>();

        for (var receipt : receipts) {
            returnValue.add(convertReceipt(receipt));
        }
        return returnValue;
    }

    public static RoleDTO convertRole(Role role) {
        if(role == null) return null;
        return  RoleDTO.builder().
                id(role.getId()).
                name(role.getName()).
                funds(convertSimpleFunds(role.getFunds())).
                build();
    }
    public static UserDTO convertUser(User user) {
        if(user == null) return null;
        return  UserDTO.builder().
                id(user.getId()).
                name(user.getName()).
                surname(user.getSurname()).
                login(user.getLogin()).
                pass(user.getPass()).
                role(convertRole(user.getRole())).
                build();
    }
}
