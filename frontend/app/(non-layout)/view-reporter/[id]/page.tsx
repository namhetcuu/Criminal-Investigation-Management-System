"use client";

import { FullReport } from "@/components/reports/FullReport";
import { ReportPreview } from "@/components/reports/ReportPreview";
import { useState } from "react";

// Sample data
// const reportData = {
//   header: {
//     reportId: "11111",
//     status: "Pending",
//     date: "05/22/2024",
//     time: "18:22",
//   },
//   personalInfo: {
//     fullName: "Nguyen Van A",
//     email: "abcd@gmail.com",
//     relationship: "Witness",
//     phone: "+1 (555) 123-4567",
//     address: {
//       street: "1234 Maplewood Avenue",
//       city: "Florida",
//       state: "CA 90026",
//       country: "United States",
//     },
//   },
//   incidentInfo: {
//     crimeType: "Crimes Against Property",
//     severity: "Moderate",
//     dateTime: "05/20/2024 14:30",
//     state: "Florida",
//     address: {
//       street: "1234 Maplewood Avenue",
//       city: "Florida",
//       state: "CA 90026",
//       country: "United States",
//     },
//     description:
//       "On the evening of June 15, 2025, at approximately 7:45 PM, I returned to my apartment at 1234 Maplewood...",
//   },
//   victims: [
//     {
//       id: "#145",
//       fullName: "Mattha, John",
//       gender: "Male",
//       nationality: "American",
//       statement:
//         "John is my father, the stolen items were his personal property...",
//     },
//   ],
//   witnesses: [
//     {
//       id: "#111",
//       fullName: "Nguyen Van A",
//       gender: "Male",
//       nationality: "American",
//       statement: "-",
//     },
//   ],
//   evidence: [
//     {
//       id: "#1",
//       type: "Digital Evidence",
//       location: "My house (1234 Maplewood Avenue Florida)",
//       description: "Image extracted from surveillance camera",
//       attachment: "File Title.mp4",
//     },
//   ],
//   uploadedFiles: [
//     {
//       name: "File Title.mp4",
//       size: "214 KB",
//       date: "31 Aug 2022",
//     },
//   ],
// };

export default function IncidentReportApp() {
  const [currentView, setCurrentView] = useState<"home" | "preview" | "full">(
    "home"
  );

  const handlePrint = () => {
    setCurrentView("full");
  };

  const handleBack = () => {
    setCurrentView("preview");
  };

  const handleClose = () => {
    setCurrentView("home");
  };

  // const handleDecline = () => {
  //   alert("Report declined!");
  //   // Implement decline logic here
  // };

  // const handleApprove = () => {
  //   alert("Report approved!");
  //   // Implement approve logic here
  // };

  if (currentView === "full") {
    return <FullReport onBack={handleBack} />;
  }

  return (
    <ReportPreview
      onPrint={handlePrint}
      onClose={handleClose}
      // onDecline={handleDecline}
      // onApprove={handleApprove}
    />
  );
}
