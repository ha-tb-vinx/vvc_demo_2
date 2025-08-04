'----------------------------------------------------------------------
'   �t�@�C�������폜����
'----------------------------------------------------------------------
'     �쐬�ҁFT.Tsubaki
'----------------------------------------------------------------------
'   �@�쐬���F2007.10.10
'   �@�X�V���F____.__.__
'----------------------------------------------------------------------
'   �X�V����
'       1.(____.__.__)      : �Q�Q�Q�Q�Q�Q�Q�Q�Q�Q�Q�Q�Q�Q�Q�Q�Q�Q�Q�Q�Q
'----------------------------------------------------------------------
'   �N�����@
'       FileDelete.vbs �t�H���_ ���ԊԊu ���ԊԊu�� [�o�b�N�A�b�v��]
'----------------------------------------------------------------------
'   �����T�v
'      �w��t�H���_���ɂāA�w����Ԃ��ȑO�̏ꍇ�ɏ����ΏۂƂ���
'      �E�o�b�N�A�b�v��̎w�肪�Ȃ��ꍇ�́A�폜
'      �E�o�b�N�A�b�v��̎w�肪����ꍇ�́A�ړ�
'          �o�b�N�A�b�v��z���ɔN���������b�̃t�H���_���쐬�������ֈړ�
'      ���Ԃ̎w����@
'          ���ԊԊu��yyyy,y,m,d,w,ww,h,n,s �̂����ꂩ
'          ���ԊԊu�������l
'          ��jd 30 �� 30�� �^ h 5 �� 5����
'----------------------------------------------------------------------
'   �T�u���[�`���ꗗ
'       1.Main_Shori        : ���C������
'       2._____________     : �Q�Q�Q�Q�Q�Q�Q�Q�Q�Q�Q�Q�Q�Q�Q�Q�Q�Q�Q�Q
'   �֐��ꗗ
'       1.CheckFoldMei      : �t�H���_���`�F�b�N
'       2._____________     : �Q�Q�Q�Q�Q�Q�Q�Q�Q�Q�Q�Q�Q�Q�Q�Q�Q�Q�Q�Q
'----------------------------------------------------------------------
Option Explicit

Dim strFolderName		'��������t�H���_��
Dim strBackupFolderName	'�o�b�N�A�b�v��
Dim objFileSystem		'�t�@�C���V�X�e���I�u�W�F�N�g
Dim strInterval			'���ԊԊu�̒P��
Dim intNumber			'���ԊԊu�̐�

'--- �������̐��`�F�b�N ---
If WScript.Arguments.Count < 3 Or WScript.Arguments.Count > 4 then
	WScript.Echo "���������G���[��" & vbCrLf & _
	             "FileDelete.vbs �`�F�b�N�t�H���_ ���ԊԊu ���ԊԊu�� �o�b�N�A�b�v��" & vbCrLf & _
	             "�@���ԊԊu�F��j 10�� �� n 10�A10�� �� d 10�A1�T�� �� ww 1" & vbCrLf & _
	             "�@�o�b�N�A�b�v��F�ȗ��\"
	WScript.Quit -1
End If

'--- �`�F�b�N����t�H���_ ---
strFolderName = CheckFoldMei(WScript.Arguments(0))

'--- �o�b�N�A�b�v��̃t�H���_ ---
If WScript.Arguments.Count = 4 Then
	strBackupFolderName = CheckFoldMei(WScript.Arguments(3))
Else
	strBackupFolderName = ""
End If

'--- ���ԊԊu�̃`�F�b�N
strInterval = WScript.Arguments(1)
Select Case strInterval
	Case "yyyy","y","m","d","w","ww", "h","n","s"
	Case Else
		'--- �z��O�̏ꍇ�A�����I�� ---
		WScript.Quit
End Select
If Not IsNumeric(WScript.Arguments(2)) Then
	'--- �������w��G���[�A�����I�� ---
	WScript.Quit
End If
intNumber = CInt(WScript.Arguments(2))

'--- �t�@�C���V�X�e���I�u�W�F�N�g�擾 ---
Set objFileSystem = WScript.CreateObject("Scripting.FileSystemObject")

Call Main_Shori()

Set objFileSystem = Nothing

WScript.Quit 0	'�����I��

'------------------------------------------------------------
'   ���C������
'------------------------------------------------------------
Sub Main_Shori()

	Dim objFolder		'�t�H���_�I�u�W�F�N�g
	Dim objFile			'��������t�@�C���I�u�W�F�N�g
	Dim objFold			'��������t�H���_�I�u�W�F�N�g
	Dim objMakeFold		'�o�b�N�A�b�v�t�H���_�쐬�p
	Dim intFlag			'�t���O(0:�o�b�N�A�b�v�L���t�H���_�쐬���^1:�o�b�N�A�b�v�Ȃ�)
						'      (2:�o�b�N�A�b�v�L���t�H���_�쐬��)

	'--- �t�H���_�I�u�W�F�N�g���擾 ---
	Set objFolder = objFileSystem.GetFolder(strFolderName)

	'--- �o�b�N�A�b�v�t�H���_�� ---
	If strBackupFolderName <> "" Then
		strBackupFolderName = strBackupFolderName & "\" & Year(Now()) & _
	                          Right("0" & Month(Now()), 2) & Right("0" & Day(Now()), 2) & _
	                          Right("0" & Hour(Now()), 2) & Right("0" & Minute(Now()), 2) & _
	                          Right("0" & Second(Now()), 2)
		intFlag = 0
	Else
		intFlag = 1
	End If

	'--- �t�H���_���̃t�@�C�������� ---
	For Each objFile In objFolder.Files
'		WScript.Echo "��" & objFile.name & " - " & objFile.DateLastModified & " - " & DateAdd(strInterval, -intNumber, Now())
		If objFile.DateLastModified < DateAdd(strInterval, -intNumber, Now()) then
			'--- �t�@�C���̍ŏI�X�V�����w����ȑO�̏ꍇ---
			If intFlag = 0 then
				'--- �o�b�N�A�b�v�t�H���_���쐬 ---
				set objMakeFold = objFileSystem.CreateFolder(strBackupFolderName)
				intFlag = 2
			End If
			If intFlag = 2 Then
'				WScript.Echo "�@�˃t�@�C���ړ��F" & objFile.name
				objFileSystem.MoveFile strFolderName & "\" & objFile.name, strBackupFolderName & "\"
			Else
'				WScript.Echo "�@�˃t�@�C���폜�F" & objFile.name
				objFile.Delete True
			End If
		Else
'			WScript.Echo "�@�ˏ����ΏۊO�F" & objFile.name
		End If
	Next

	'--- �t�H���_���̃t�H���_������ ---
	For Each objFold In objFolder.SubFolders
		WScript.Echo "��" & objFold.name & " - " & objFold.DateLastModified & " - " & DateAdd(strInterval, -intNumber, Now())
		If objFold.DateLastModified < DateAdd(strInterval, -intNumber, Now()) then
			'--- �t�@�C���̍ŏI�X�V�����w����ȑO�̏ꍇ---
			If intFlag = 0 then
				'--- �o�b�N�A�b�v�t�H���_���쐬 ---
				set objMakeFold = objFileSystem.CreateFolder(strBackupFolderName)
				intFlag = 2
			End If
			If intFlag = 2 Then
				WScript.Echo "�@�˃t�H���_�ړ��F" & objFold.name
				objFileSystem.CopyFolder strFolderName & "\" & objFold.name, strBackupFolderName & "\"
				objFold.Delete True
			Else
				WScript.Echo "�@�˃t�H���_�폜�F" & objFold.name
				objFold.Delete True
			End If
		Else
			WScript.Echo "�@�ˏ����ΏۊO�F" & objFold.name
		End If
	Next

	Set objFolder = Nothing
	Set objFile   = Nothing
	Set objFold   = Nothing

End Sub

'------------------------------------------------------------
'   �t�H���_���`�F�b�N
'------------------------------------------------------------
Function CheckFoldMei(strFold)
	If Right(strFold, 1) = "\" Then
		CheckFoldMei = Left(strFold, Len(strFold) - 1)
	Else
		CheckFoldMei = strFold
	End If
End Function
