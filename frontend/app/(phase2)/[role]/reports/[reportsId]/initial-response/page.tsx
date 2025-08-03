"use client";

import { useState } from "react";
import { Input } from "@/components/ui/input";
import { Button } from "@/components/ui/button";
import { DatetimePicker } from "@/components/features/phase2/DateTimePicker";
import { SectionContainer } from "@/components/features/phase2/SectionContainer";
import { DataTable } from "@/components/features/phase2/DataTable";
import { ActionButtons } from "@/components/features/phase2/ActionButtons";
import { DeleteEvidenceModal } from "@/components/features/phase2/DeleteEvidenceModal";
import { useRouter, useParams } from "next/navigation";

/**
 * INITIAL RESPONSE PAGE
 *
 * This is the main page component for the Initial Response section of the police report system.
 * It displays various forms and tables for collecting initial response data including:
 * - Time of dispatching forces
 * - Time of arrival at scene
 * - List of officers assigned
 * - Preliminary assessment of scene situation
 * - Scene preservation measures
 * - Medical/rescue support information
 *
 * The page uses reusable components (SectionContainer, DataTable, ActionButtons)
 * to maintain consistency and reduce code duplication.
 */

// Initial mock data for different sections
// These would typically come from a database or API
const initialStatementInit = [
	{
		id: "PE 01",
		location: "A1 - Kitchen",
		collector: "Lt. James Potter",
		time: "14:25 - 25/06/25",
	},
	{
		id: "PE 02",
		location: "B2 - Living Room",
		collector: "Sgt. Ben Whatley",
		time: "14:37 - 25/06/25",
	},
];

const preservationInit = [
	"Immediate perimeter established using police tape (approx. 30-meter radius)",
	"Vehicle stabilized to prevent further movement.",
	"Photographic documentation of the scene commenced at 22:26.",
];

const medicalInit = [
	{ id: "EMS45", type: "Medical Emergency", time: "08:00 PM" },
	{ id: "RES-012", type: "Patrol Officer", time: "08:00 PM" },
	{ id: "RES-012", type: "Detective", time: "08:00 PM" },
];

const officersInit = [
	{ name: "Brandie", role: "Patrol Officer", phone: "(225) 555-0118" },
	{ name: "Brandie", role: "Patrol Officer", phone: "(225) 555-0118" },
	{ name: "Brandie", role: "Detective", phone: "(225) 555-0118" },
];

export default function Page() {
	// State management for all data sections
	const [statements, setStatements] = useState(initialStatementInit);
	const [preservations, setPreservations] = useState(preservationInit);
	const [medical, setMedical] = useState(medicalInit);
	const [officers, setOfficers] = useState(officersInit);
	const [isAM, setIsAM] = useState(true);

	// State for controlling the delete modal
	const [showDeleteModal, setShowDeleteModal] = useState(false);
	const [evidenceToDelete, setEvidenceToDelete] = useState<any>(null);

	// Router and params
	const router = useRouter();
	const params = useParams(); // Get dynamic route parameters

	/**
	 * COLUMN CONFIGURATIONS
	 *
	 * These objects define the structure of each table:
	 * - key: the property name in the data object
	 * - label: the display name for the column header
	 * - render: optional custom render function for special formatting
	 */
	const officerColumns = [
		{ key: "name", label: "Full Name" },
		{ key: "role", label: "Role" },
		{ key: "phone", label: "Phone Number" },
	];

	const statementColumns = [
		{ key: "id", label: "ID" },
		{ key: "location", label: "Location" },
		{ key: "collector", label: "Collector" },
		{ key: "time", label: "Time" },
	];

	const preservationColumns = [
		{
			key: "number",
			label: "#",
			// Custom render function to show row number (index + 1)
			render: (_: any, __: any, index: number) => index + 1,
		},
		{ key: "measure", label: "Preservation Measures" },
	];

	const medicalColumns = [
		{ key: "id", label: "Medical/Rescue Unit ID" },
		{ key: "type", label: "Type of Support Provided" },
		{ key: "time", label: "Time of Arrival" },
	];

	/**
	 * EVENT HANDLERS
	 *
	 * These functions handle user interactions like adding, editing, and deleting items.
	 * Currently they just log to console, but in a real application they would:
	 * - Open modals/forms for editing
	 * - Make API calls to save/update/delete data
	 * - Update the local state
	 * - Show success/error messages
	 */

	// Add handlers - would typically open a form modal
	const handleAddOfficer = () => {
		console.log("Add officer");
		// TODO: Open add officer modal/form
	};

	const handleAddStatement = () => {
		console.log("Add statement");
		// TODO: Open add statement modal/form
	};

	const handleAddPreservation = () => {
		console.log("Add preservation measure");
		// TODO: Open add preservation modal/form
	};

	const handleAddMedical = () => {
		console.log("Add medical support");
		// TODO: Open add medical support modal/form
	};

	// Edit handlers - would typically open a pre-filled form modal
	const handleEditOfficer = (officer: any, index: number) => {
		console.log("Edit officer:", officer, index);
		// TODO: Open edit officer modal with pre-filled data
	};

	const handleEditPreservation = (preservation: any, index: number) => {
		console.log("Edit preservation:", preservation, index);
		// TODO: Open edit preservation modal with pre-filled data
	};

	const handleEditMedical = (medical: any, index: number) => {
		console.log("Edit medical:", medical, index);
		// TODO: Open edit medical modal with pre-filled data
	};

	// Delete handlers - updated to show confirmation modal
	const handleDeleteOfficer = (officer: any, index: number) => {
		setEvidenceToDelete({ item: officer, index, type: "officer" });
		setShowDeleteModal(true);
	};

	const handleDeletePreservation = (preservation: any, index: number) => {
		setEvidenceToDelete({ item: preservation, index, type: "preservation" });
		setShowDeleteModal(true);
	};

	const handleDeleteMedical = (medical: any, index: number) => {
		setEvidenceToDelete({ item: medical, index, type: "medical" });
		setShowDeleteModal(true);
	};

	// View handlers - would typically open a detail view or navigate to detail page
	const handleViewMedical = (medical: any, index: number) => {
		console.log("View medical:", medical, index);
		// TODO: Open medical detail modal or navigate to detail page
	};

	const handleViewDetails = (statement: any, index: number) => {
		console.log("View details:", statement, index);
		// Navigate to assessment detail page with dynamic parameters
		router.push(
			`/${params.role}/reports/${params.reportsId}/initial-response/assessment`
		);
	};

	// Confirm deletion handler
	const handleConfirmDelete = () => {
		if (!evidenceToDelete) return;

		const { item, index, type } = evidenceToDelete;

		// Perform actual deletion based on type
		switch (type) {
			case "officer":
				setOfficers((prev) => prev.filter((_, i) => i !== index));
				console.log("Officer deleted:", item, index);
				break;
			case "preservation":
				setPreservations((prev) => prev.filter((_, i) => i !== index));
				console.log("Preservation measure deleted:", item, index);
				break;
			case "medical":
				setMedical((prev) => prev.filter((_, i) => i !== index));
				console.log("Medical support deleted:", item, index);
				break;
		}

		// Reset modal state
		setEvidenceToDelete(null);
		setShowDeleteModal(false);
	};

	// Close modal handler
	const handleCloseModal = () => {
		setShowDeleteModal(false);
		setEvidenceToDelete(null);
	};

	/**
	 * DATA TRANSFORMATION
	 *
	 * The preservation data is stored as an array of strings, but the DataTable
	 * component expects objects. This transforms the string array into an object array
	 * where each string becomes the 'measure' property.
	 */
	const preservationData = preservations.map((text) => ({ measure: text }));

	return (
		<main className="flex-1 p-6">
			{/* Page Header */}
			<h1 className="text-3xl font-bold text-center bg-blue-100 text-blue-900 px-4 py-2 rounded-t-lg shadow">
				INITIAL RESPONSE
			</h1>

			{/* Main Content Container with gray background */}
			<div className="bg-gray-300 rounded-b-lg shadow p-6 pt-10" id="dispatch-time">
				{/* TIME OF DISPATCHING SECTION */}
				{/* Simple section with just a label and choose button */}
				<div className="mb-6 bg-white p-4 rounded-lg">
					<div className="flex justify-between items-center mb-2">
						<label className="font-semibold text-md">
							TIME OF DISPATCHING FORCES TO THE SCENE
						</label>
						<DatetimePicker />
					</div>
				</div>

				{/* TIME OF ARRIVAL SECTION */}
				{/* Section with time input and AM/PM toggle */}
				<div className="mb-6 bg-white p-4 rounded-lg" id="arrival-time">
					<div className="flex justify-between items-center">
						<label className="font-semibold text-sm">
							TIME OF ARRIVAL AT THE SCENE
						</label>
						<div className="flex items-center gap-2">
							{/* Time input field */}
							<Input className="w-20 rounded-md" value="09:32" readOnly />
							{/* AM/PM toggle */}
							<div className="flex items-center justify-center bg-gray-200 rounded-md w-24 h-8 px-1 relative">
								<button
									type="button"
									className={`flex-1 h-6 rounded-md text-sm font-medium focus:outline-none transition-colors duration-200 ${isAM
											? 'bg-white text-blue-900 shadow'
											: 'text-gray-500 hover:text-gray-700'
										}`}
									style={{ marginRight: "2px" }}
									onClick={() => setIsAM(true)}
								>
									AM
								</button>
								<button
									type="button"
									className={`flex-1 h-6 rounded-md text-sm font-medium focus:outline-none transition-colors duration-200 ${!isAM
											? 'bg-white text-blue-900 shadow'
											: 'text-gray-500 hover:text-gray-700'
										}`}
									onClick={() => setIsAM(false)}
								>
									PM
								</button>
							</div>
						</div>
					</div>
				</div>

				{/* OFFICERS TABLE SECTION */}
				{/* Uses SectionContainer and DataTable components with ActionButtons */}
				<SectionContainer
					label="LIST OF OFFICERS ASSIGNED TO THE SCENE"
					onAdd={handleAddOfficer}
				>
					<DataTable
						columns={officerColumns}
						data={officers}
						actions={(row, index) => (
							<ActionButtons
								row={row}
								index={index}
								onEdit={handleEditOfficer}
								onDelete={handleDeleteOfficer}
							// No onView prop = no view button will be rendered
							/>
						)}
					/>
				</SectionContainer>

				{/* PRELIMINARY ASSESSMENT SECTION */}
				{/* Uses custom action button instead of ActionButtons component */}
				<SectionContainer
					label="PRELIMINARY ASSESSMENT OF THE SCENE SITUATION"
					onAdd={handleAddStatement}
				>
					<DataTable
						columns={statementColumns}
						data={statements}
						actions={(row, index) => (
							<Button
								size="sm"
								variant="outline"
								className="bg-blue-100 rounded-md me-10"
								onClick={() => handleViewDetails(row, index)}
							>
								View details
							</Button>
						)}
					/>
				</SectionContainer>

				{/* SCENE PRESERVATION SECTION */}
				{/* Uses transformed data (preservationData) and ActionButtons */}
				<SectionContainer
					label="SCENE PRESERVATION MEASURES TAKEN"
					onAdd={handleAddPreservation}
				>
					<DataTable
						columns={preservationColumns}
						data={preservationData}
						actions={(row, index) => (
							<ActionButtons
								row={row}
								index={index}
								onEdit={handleEditPreservation}
								onDelete={handleDeletePreservation}
							// No onView prop = no view button
							/>
						)}
					/>
				</SectionContainer>

				{/* MEDICAL/RESCUE SUPPORT SECTION */}
				{/* Uses ActionButtons with all three actions (edit, delete, view) */}
				<SectionContainer
					label="INFORMATION ON MEDICAL/RESCUE SUPPORT PROVIDED"
					onAdd={handleAddMedical}
					className="mb-8" // Custom margin bottom
				>
					<DataTable
						columns={medicalColumns}
						data={medical}
						actions={(row, index) => (
							<ActionButtons
								row={row}
								index={index}
								onEdit={handleEditMedical}
								onDelete={handleDeleteMedical}
								onView={handleViewMedical} // This adds the view button
							/>
						)}
					/>
				</SectionContainer>

				{/* PAGE ACTION BUTTONS */}
				{/* Cancel and Save buttons for the entire form */}
				<div className="flex justify-end gap-4 bg-white p-4 rounded-lg">
					<Button variant="outline" className="rounded-full">
						Cancel
					</Button>
					<Button className="rounded-full">Save</Button>
				</div>
			</div>

			{/* DELETE CONFIRMATION MODAL */}
			<DeleteEvidenceModal
				isOpen={showDeleteModal}
				onClose={handleCloseModal}
				onConfirm={handleConfirmDelete}
				evidenceName={
					evidenceToDelete?.item?.name ||
					evidenceToDelete?.item?.id ||
					evidenceToDelete?.item?.measure ||
					"this evidence"
				}
			/>
		</main>
	);
}