'----------------------------------------------------------------------
'   ファイル自動削除処理
'----------------------------------------------------------------------
'     作成者：T.Tsubaki
'----------------------------------------------------------------------
'   　作成日：2007.10.10
'   　更新日：____.__.__
'----------------------------------------------------------------------
'   更新履歴
'       1.(____.__.__)      : ＿＿＿＿＿＿＿＿＿＿＿＿＿＿＿＿＿＿＿＿＿
'----------------------------------------------------------------------
'   起動方法
'       FileDelete.vbs フォルダ 時間間隔 時間間隔数 [バックアップ先]
'----------------------------------------------------------------------
'   処理概要
'      指定フォルダ内にて、指定期間より以前の場合に処理対象とする
'      ・バックアップ先の指定がない場合は、削除
'      ・バックアップ先の指定がある場合は、移動
'          バックアップ先配下に年月日時分秒のフォルダを作成しそこへ移動
'      期間の指定方法
'          時間間隔＝yyyy,y,m,d,w,ww,h,n,s のいずれか
'          時間間隔数＝数値
'          例）d 30 ＝ 30日 ／ h 5 ＝ 5時間
'----------------------------------------------------------------------
'   サブルーチン一覧
'       1.Main_Shori        : メイン処理
'       2._____________     : ＿＿＿＿＿＿＿＿＿＿＿＿＿＿＿＿＿＿＿＿
'   関数一覧
'       1.CheckFoldMei      : フォルダ名チェック
'       2._____________     : ＿＿＿＿＿＿＿＿＿＿＿＿＿＿＿＿＿＿＿＿
'----------------------------------------------------------------------
Option Explicit

Dim strFolderName		'検索するフォルダ名
Dim strBackupFolderName	'バックアップ先
Dim objFileSystem		'ファイルシステムオブジェクト
Dim strInterval			'時間間隔の単位
Dim intNumber			'時間間隔の数

'--- 引き数の数チェック ---
If WScript.Arguments.Count < 3 Or WScript.Arguments.Count > 4 then
	WScript.Echo "★引き数エラー★" & vbCrLf & _
	             "FileDelete.vbs チェックフォルダ 時間間隔 時間間隔数 バックアップ先" & vbCrLf & _
	             "　時間間隔：例） 10分 → n 10、10日 → d 10、1週間 → ww 1" & vbCrLf & _
	             "　バックアップ先：省略可能"
	WScript.Quit -1
End If

'--- チェックするフォルダ ---
strFolderName = CheckFoldMei(WScript.Arguments(0))

'--- バックアップ先のフォルダ ---
If WScript.Arguments.Count = 4 Then
	strBackupFolderName = CheckFoldMei(WScript.Arguments(3))
Else
	strBackupFolderName = ""
End If

'--- 時間間隔のチェック
strInterval = WScript.Arguments(1)
Select Case strInterval
	Case "yyyy","y","m","d","w","ww", "h","n","s"
	Case Else
		'--- 想定外の場合、処理終了 ---
		WScript.Quit
End Select
If Not IsNumeric(WScript.Arguments(2)) Then
	'--- 引き数指定エラー、処理終了 ---
	WScript.Quit
End If
intNumber = CInt(WScript.Arguments(2))

'--- ファイルシステムオブジェクト取得 ---
Set objFileSystem = WScript.CreateObject("Scripting.FileSystemObject")

Call Main_Shori()

Set objFileSystem = Nothing

WScript.Quit 0	'処理終了

'------------------------------------------------------------
'   メイン処理
'------------------------------------------------------------
Sub Main_Shori()

	Dim objFolder		'フォルダオブジェクト
	Dim objFile			'処理するファイルオブジェクト
	Dim objFold			'処理するフォルダオブジェクト
	Dim objMakeFold		'バックアップフォルダ作成用
	Dim intFlag			'フラグ(0:バックアップ有＆フォルダ作成未／1:バックアップなし)
						'      (2:バックアップ有＆フォルダ作成済)

	'--- フォルダオブジェクトを取得 ---
	Set objFolder = objFileSystem.GetFolder(strFolderName)

	'--- バックアップフォルダ名 ---
	If strBackupFolderName <> "" Then
		strBackupFolderName = strBackupFolderName & "\" & Year(Now()) & _
	                          Right("0" & Month(Now()), 2) & Right("0" & Day(Now()), 2) & _
	                          Right("0" & Hour(Now()), 2) & Right("0" & Minute(Now()), 2) & _
	                          Right("0" & Second(Now()), 2)
		intFlag = 0
	Else
		intFlag = 1
	End If

	'--- フォルダ内のファイルを処理 ---
	For Each objFile In objFolder.Files
'		WScript.Echo "◆" & objFile.name & " - " & objFile.DateLastModified & " - " & DateAdd(strInterval, -intNumber, Now())
		If objFile.DateLastModified < DateAdd(strInterval, -intNumber, Now()) then
			'--- ファイルの最終更新日が指定日以前の場合---
			If intFlag = 0 then
				'--- バックアップフォルダを作成 ---
				set objMakeFold = objFileSystem.CreateFolder(strBackupFolderName)
				intFlag = 2
			End If
			If intFlag = 2 Then
'				WScript.Echo "　⇒ファイル移動：" & objFile.name
				objFileSystem.MoveFile strFolderName & "\" & objFile.name, strBackupFolderName & "\"
			Else
'				WScript.Echo "　⇒ファイル削除：" & objFile.name
				objFile.Delete True
			End If
		Else
'			WScript.Echo "　⇒処理対象外：" & objFile.name
		End If
	Next

	'--- フォルダ内のフォルダを処理 ---
	For Each objFold In objFolder.SubFolders
		WScript.Echo "◆" & objFold.name & " - " & objFold.DateLastModified & " - " & DateAdd(strInterval, -intNumber, Now())
		If objFold.DateLastModified < DateAdd(strInterval, -intNumber, Now()) then
			'--- ファイルの最終更新日が指定日以前の場合---
			If intFlag = 0 then
				'--- バックアップフォルダを作成 ---
				set objMakeFold = objFileSystem.CreateFolder(strBackupFolderName)
				intFlag = 2
			End If
			If intFlag = 2 Then
				WScript.Echo "　⇒フォルダ移動：" & objFold.name
				objFileSystem.CopyFolder strFolderName & "\" & objFold.name, strBackupFolderName & "\"
				objFold.Delete True
			Else
				WScript.Echo "　⇒フォルダ削除：" & objFold.name
				objFold.Delete True
			End If
		Else
			WScript.Echo "　⇒処理対象外：" & objFold.name
		End If
	Next

	Set objFolder = Nothing
	Set objFile   = Nothing
	Set objFold   = Nothing

End Sub

'------------------------------------------------------------
'   フォルダ名チェック
'------------------------------------------------------------
Function CheckFoldMei(strFold)
	If Right(strFold, 1) = "\" Then
		CheckFoldMei = Left(strFold, Len(strFold) - 1)
	Else
		CheckFoldMei = strFold
	End If
End Function
