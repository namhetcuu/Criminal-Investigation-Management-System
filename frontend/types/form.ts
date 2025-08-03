export interface ReporterInfo {
  fullName: string
  email: string
  phoneNumber: string
  address: string
  relationship: string
}

export interface IncidentInfo {
  crimeType: string
  severity: string
  datetime: string
  detailedAddress: string
  description: string
}

export interface FormData extends ReporterInfo, IncidentInfo {}

export interface TableRow {
  id: string
  [key: string]: string
}
