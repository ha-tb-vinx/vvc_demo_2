REM @echo off
REM ============================================================================
REM タイトル	：属性情報取得処理（レジ別取引点検）
REM 説明	：属性情報取得処理（レジ別取引点検）を実行する。
REM version 3.00 2013/10/18 T.Ooshiro:新規
REM version 3.01 2014/02/03 Y.Tominaga:結合ﾃｽﾄNo.0112 時間帯関連バッチ_POS時間帯取込処理改善
REM ============================================================================

SET BATCHF_HOME=@filter.shellscript.baseDirectory.value@

CALL %BATCHF_HOME%\batch\SetBatchPath.bat

REM ===== 属性情報取得処理（レジ別取引点検）
%EXECJAVA% jp.co.vinculumjapan.swc.commons.dao.BatchController URIB013230
IF %ERRORLEVEL% NEQ 0 goto error

:end

CD %BATCHF_HOME%

exit 0

:error

exit 1
